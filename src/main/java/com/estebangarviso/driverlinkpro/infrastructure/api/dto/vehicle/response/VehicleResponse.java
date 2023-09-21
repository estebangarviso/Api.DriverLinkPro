package com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.response;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VehicleResponse {

    @Schema(description = "Vehicle's id", example = "1")
    private Long id;

    @Schema(description = "Vehicle's code", example = "19efb892-00d3-470f-ac7c-25b82f5cd329")
    private String code;

    @Schema(description = "Vehicle's driver", implementation = DriverResponse.class)
    private DriverResponse driver;
}
