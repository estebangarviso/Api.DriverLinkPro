package com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class VehicleResponse {

    @Schema(description = "Vehicle's id", example = "1")
    private Long id;

    @Schema(description = "Vehicle's code", example = "a57a9c2ec5456cf09d9c2a910a3a537262a3af62b067d835ecfcb3d6f72a6037")
    private String code;
}
