package com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class VehicleRequestBodyDto {
    @Schema(description = "Vehicle's driver id", example = "1")
    @NotEmpty(message = "Driver id is required")
    @Positive(message = "Driver id must be positive")
    @Pattern(regexp = "^[0-9]*$", message = "Driver id must be a number")
    private Long driverId;
}
