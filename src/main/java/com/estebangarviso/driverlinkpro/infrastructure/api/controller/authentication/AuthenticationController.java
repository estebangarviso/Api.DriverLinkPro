package com.estebangarviso.driverlinkpro.infrastructure.api.controller.authentication;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignInRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request.SignUpRequest;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.response.AuthenticationResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.authentication_provider.JwtAuthenticationProvider;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @PostMapping("/signup")
    public AuthenticationResponse signup(@RequestBody SignUpRequest request) {

        return jwtAuthenticationProvider.signUp(request);
    }

    @PostMapping("/signin")
    public AuthenticationResponse signin(@RequestBody SignInRequest request) {

        return jwtAuthenticationProvider.signIn(request);
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtAuthenticationProvider.refreshToken(request, response);
    }

    @GetMapping("/confirm-email/{securityToken}")
    public void confirmEmail(@PathVariable String securityToken) {
        jwtAuthenticationProvider.confirmEmail(securityToken);
    }
}
