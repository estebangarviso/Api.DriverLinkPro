package com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignInRequestBodyDto {
    @Schema(description = "User's email", example = "demo@demo.com")
    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    @Schema(description = "User's password", example = "Sup3r$3cr3t")
    @NotEmpty(message = "Password is required")
    private String password;
}

