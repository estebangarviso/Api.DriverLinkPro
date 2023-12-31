package com.estebangarviso.driverlinkpro.infrastructure.api.dto.authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequestBodyDto {
    @Schema(description = "User's first name", example = "John")
    @NotEmpty(message = "First name is required")
    @Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    @NotEmpty(message = "Last name is required")
    @Size(min = 3, max = 100, message = "Last name must be between 3 and 100 characters")
    private String lastName;

    @Schema(description = "User's email", example = "demo@demo.com")
    @NotEmpty(message = "Email is required")
    @Size(max = 95, message = "Email must be less or equal than 95 characters")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "User's password", example = "Sup3r$3cr3t")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Size(max = 32, message = "Password must be less or equal than 32 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,32}$", message = "Password must contain at least one lowercase letter, one uppercase letter, one number and one special character")
    private String password;
}
