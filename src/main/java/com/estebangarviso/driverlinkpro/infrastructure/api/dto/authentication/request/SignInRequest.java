package com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @Schema(description = "Driver's email", example = "demo@demo.com")
    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    @Schema(description = "Driver's password", example = "Sup3r$3cr3t")
    @NotEmpty(message = "Password is required")
    private String password;
}

