package com.estebangarviso.driverlinkpro.infrastructure.authentication.authentication_provider;


import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token.Token;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.token.TokenType;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.TokenRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.smtp.SMTPAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserRole;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.User;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.authentication.AuthenticationRepositoryJpa;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignInRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.AuthenticationResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.jwt_provider.JwtProvider;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private SMTPAdapter smtpAdapter;
    private TokenRepository tokenRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private AuthenticationRepositoryJpa authenticationRepositoryJpa;

    public AuthenticationResponse signUp(SignUpRequest request) {
        // TODO: Add captcha validation
        // Check if user already exists
        if (authenticationRepositoryJpa.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("User already exists");
        var securityToken = UUID.randomUUID().toString();
        var user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setIsEnabled(false);
        user.setSecurityToken(securityToken);

        authenticationRepositoryJpa.save(user);

        var accessToken = jwtProvider.generateToken(user);
        var refreshToken = jwtProvider.generateRefreshToken(user);

        saveUserToken(user, accessToken);

        smtpAdapter.Send(
            "noreply@driverlinkpro.com",
            "Driver Link Pro",
            "Confirm your email",
            "Confirm your email by clicking on the link below: \n\n" + "http://localhost:8080/api/v1/auth/confirm/" + securityToken, request.getEmail(),
            "true"
        );

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

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
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

    public void confirm(String token) {
        var user = authenticationRepositoryJpa.findBySecurityToken(token)
                .orElseThrow();
        user.setIsEnabled(true);
        authenticationRepositoryJpa.save(user);
    }
}

