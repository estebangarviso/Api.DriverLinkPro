package com.estebangarviso.driverlinkpro.infrastructure.authentication.authentication_provider;



import com.estebangarviso.driverlinkpro.domain.exception.general.BadRequestException;
import com.estebangarviso.driverlinkpro.domain.exception.general.NotFoundException;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepository;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignInRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.AuthenticationResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.jwt_provider.JwtProvider;
import com.estebangarviso.driverlinkpro.domain.service.mail.MailContentBuilderService;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token.TokenEntity;
import com.estebangarviso.driverlinkpro.domain.model.token.TokenType;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.token.TokenRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.smtp.SMTPAdapter;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Transactional
public class JwtAuthenticationProvider {

    private final SMTPAdapter smtpAdapter;
    private final MailContentBuilderService mailContentBuilderService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationRepository authenticationRepository;

    @Value("${application.uri}")
    private String serverUri;

    public AuthenticationResponse signUp(SignUpRequestBodyDto request) {
        // TODO: Add captcha validation
        // Check if user already exists
        if (authenticationRepository.existsByEmail(request.getEmail()))
            throw BadRequestException.userAlreadyExists();
        var securityToken = UUID.randomUUID().toString();
        var user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addRole(UserRole.USER);
        user.setIsEnabled(false);
        user.setSecurityToken(securityToken);

        authenticationRepository.save(user);
        try {
            sendConfirmationEmail(request.getFirstName(), request.getLastName(), request.getEmail(), securityToken);
        } catch (Exception e) {
            throw BadRequestException.emailNotSent("Confirmation email could not be sent", e);
        }

        var accessToken = jwtProvider.generateToken(user);
        var refreshToken = jwtProvider.generateRefreshToken(user);

        saveUserToken(user, accessToken);

        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse signIn(SignInRequestBodyDto request) {
        // TODO: Add captcha validation
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = authenticationRepository.findByEmail(request.getEmail())
                .orElseThrow(BadRequestException::invalidCredentials);

        var accessToken = jwtProvider.generateToken(user);
        var refreshToken = jwtProvider.generateRefreshToken(user);

        saveUserToken(user, accessToken);

        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .value(jwtToken)
                .type(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllValidValueByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    private void sendConfirmationEmail(String firstName, String lastName, String to, String securityToken) {
        URI uri;
        String uriString;

        uri = URI.create(serverUri);
        uriString = uri.toString();

        var emailBody = mailContentBuilderService
                .addVariables(new HashMap<>() {{
                    put("firstName", firstName);
                    put("lastName", lastName);
                    put("confirmationLink", uriString + "/api/v1/auth/confirm-email/" + securityToken);
                }})
                .setTemplate("html/email-confirmation.html")
                .build();

        smtpAdapter.send(
            "Confirm your email",
            emailBody,
            to,
            "true"
        );
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtProvider.extractUserName(refreshToken);
        if (userEmail != null) {
            var user = authenticationRepository.findByEmail(userEmail)
                    .orElseThrow(NotFoundException::userNotFound);
            if (jwtProvider.isTokenValid(refreshToken, user)) {
                var accessToken = jwtProvider.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void confirmEmail(String securityToken) {
        var user = authenticationRepository.findBySecurityToken(securityToken)
                .orElseThrow(NotFoundException::userNotFound);
        user.setIsEnabled(true);
        authenticationRepository.save(user);
    }
}

