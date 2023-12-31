package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.vehicle;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.response.*;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Vehicle", description = "Vehicle API")
public interface VehicleSwagger {
        @Operation(summary = "Create a new vehicle", description = "Add a new vehicle to the application")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        VehicleResponse createVehicle(VehicleRequestBodyDto vehicleRequestBodyDto);

        @Operation(summary = "Get a vehicle", description = "Get a vehicle by id")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        VehicleResponse getVehicle(Long id);

        @Operation(summary = "Update a vehicle", description = "Update a vehicle by id")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = VehicleResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        VehicleResponse updateVehicle(Long id, VehicleRequestBodyDto vehicleRequestBodyDto);

        @Operation(summary = "Delete a vehicle", description = "Delete a vehicle by id")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content(schema = @Schema(implementation = ResponseStatus.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        void deleteVehicle(Long id);
}
