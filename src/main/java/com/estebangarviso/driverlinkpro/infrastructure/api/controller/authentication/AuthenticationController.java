package com.estebangarviso.driverlinkpro.infrastructure.api.controller.authentication;


import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.authentication.AuthenticationSwagger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignInRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.AuthenticationResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.authentication_provider.JwtAuthenticationProvider;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthenticationController implements AuthenticationSwagger {

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "User created")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return jwtAuthenticationProvider.signUp(request);
    }

    @PostMapping("/signin")
    @ResponseStatus(code = HttpStatus.OK, reason = "User logged in")
    public AuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return jwtAuthenticationProvider.signIn(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(code = HttpStatus.OK, reason = "Token refreshed")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtAuthenticationProvider.refreshToken(request, response);
    }

    @GetMapping("/confirm-email/{securityToken}")
    @ResponseStatus(code = HttpStatus.OK, reason = "Email confirmed")
    public void confirmEmail(@PathVariable String securityToken) {
        jwtAuthenticationProvider.confirmEmail(securityToken);
    }
}
