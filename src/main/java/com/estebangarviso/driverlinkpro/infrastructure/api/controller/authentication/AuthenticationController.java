package com.estebangarviso.driverlinkpro.infrastructure.api.controller.authentication;


import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.authentication.AuthenticationSwagger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignInRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.AuthenticationResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.authentication_provider.JwtAuthenticationProvider;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController implements AuthenticationSwagger {

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequestBodyDto request) {
        return jwtAuthenticationProvider.signUp(request);
    }

    @PostMapping("/signin")
    public AuthenticationResponse signIn(@RequestBody @Valid SignInRequestBodyDto request) {
        return jwtAuthenticationProvider.signIn(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtAuthenticationProvider.refreshToken(request, response);
    }

    @GetMapping("/confirm-email/{securityToken}")
    public void confirmEmail(@PathVariable String securityToken) {
        jwtAuthenticationProvider.confirmEmail(securityToken);
    }
}
