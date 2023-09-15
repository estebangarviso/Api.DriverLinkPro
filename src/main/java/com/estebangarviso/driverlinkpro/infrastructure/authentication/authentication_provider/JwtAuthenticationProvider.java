package com.estebangarviso.driverlinkpro.infrastructure.authentication.authentication_provider;



import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.estebangarviso.driverlinkpro.domain.model.user.UserRole;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepositoryJpa;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignInRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.AuthenticationResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.jwt_provider.JwtProvider;
import com.estebangarviso.driverlinkpro.domain.service.mail.MailContentBuilderService;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token.TokenEntity;
import com.estebangarviso.driverlinkpro.domain.model.token.TokenType;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.token.TokenRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.smtp.SMTPAdapter;

import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

@AllArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private SMTPAdapter smtpAdapter;
    private MailContentBuilderService mailContentBuilderService;
    private TokenRepository tokenRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private AuthenticationRepositoryJpa authenticationRepositoryJpa;

    public AuthenticationResponse signUp(SignUpRequest request) {
        // TODO: Add captcha validation
        // Check if user already exists
        if (authenticationRepositoryJpa.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("UserEntity already exists");
        var securityToken = UUID.randomUUID().toString();
        var userRoles = new HashSet<UserRole>();
        var user = new UserEntity();
        userRoles.add(UserRole.USER);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(userRoles);
        user.setIsEnabled(false);
        user.setSecurityToken(securityToken);

        authenticationRepositoryJpa.save(user);

        var accessToken = jwtProvider.generateToken(user);
        var refreshToken = jwtProvider.generateRefreshToken(user);

        saveUserToken(user, accessToken);
        sendConfirmationEmail(request.getFirstName(), request.getLastName(), request.getEmail(), securityToken);

        return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public AuthenticationResponse signIn(SignInRequest request) {
        // TODO: Add captcha validation
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = authenticationRepositoryJpa.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

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
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void sendConfirmationEmail(String firstName, String lastName, String email, String securityToken) {
        var uriComponents = ServletUriComponentsBuilder.fromCurrentContextPath().build();
        var uriString = uriComponents.toUriString();
        var host = uriComponents.getHost();
        var emailBody = mailContentBuilderService
                .addVariable("firstName", lastName)
                .addVariable("lastName", firstName)
                .addVariable("confirmationLink", uriString + "/api/v1/auth/confirm-email/" + securityToken)
                .setTemplate("email-confirmation")
                .build();

        smtpAdapter.send(
                "noreply@"+host,
                "DriverEntity Link Pro",
                "Confirm your email",
                emailBody,
                email,
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
            var user = authenticationRepositoryJpa.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtProvider.isTokenValid(refreshToken, user)) {
                var accessToken = jwtProvider.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void confirmEmail(String securityToken) {
        var user = authenticationRepositoryJpa.findBySecurityToken(securityToken)
                .orElseThrow();
        user.setIsEnabled(true);
        authenticationRepositoryJpa.save(user);
    }
}

