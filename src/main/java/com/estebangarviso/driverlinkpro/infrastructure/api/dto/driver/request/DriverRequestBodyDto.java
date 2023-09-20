package com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequestBodyDto {
    @Schema(description = "Driver's name", example = "John Doe")
    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Schema(description = "Driver's cellphone", example = "+56912345678")
    @NotEmpty(message = "Cellphone is required")
    @Size(min = 12, max = 15, message = "Cellphone must be between 12 and 15 characters")
    private String cellphone;

    @Schema(description = "Driver's email", example = "demo@demo.com")
    @NotEmpty(message = "Email is required")
    @Size(max = 95, message = "Email must be less or equal than 95 characters")
    @Email(message = "Email must be valid")
    private String email;
}
