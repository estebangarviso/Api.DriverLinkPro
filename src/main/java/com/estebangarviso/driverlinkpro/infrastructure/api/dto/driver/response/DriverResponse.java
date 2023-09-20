package com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class DriverResponse {
    @Schema(description = "Driver's id", example = "1")
    private Long id;

    @Schema(description = "Driver's code", example = "D-1")
    private String code;

    @Schema(description = "Driver's name", example = "John Doe")
    private String name;

    @Schema(description = "Driver's cellphone", example = "+56912345678")
    private String cellphone;

    @Schema(description = "Driver's email", example = "demo@demo.com")
    private String email;
}
