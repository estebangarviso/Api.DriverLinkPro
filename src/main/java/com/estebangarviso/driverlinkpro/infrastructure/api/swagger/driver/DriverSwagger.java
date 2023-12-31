package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.driver;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.*;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Driver", description = "Driver API")
public interface DriverSwagger {
        @Operation(summary = "Create a new driver", description = "Add a new driver to the application")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = DriverResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        DriverResponse createDriver(DriverRequestBodyDto driverRequestBodyDto);

        @Operation(summary = "Get a driver", description = "Get a driver by id")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DriverResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        DriverResponse getDriver(Long id);

        @Operation(summary = "Update a driver", description = "Update a driver by id")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = DriverResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        DriverResponse updateDriver(Long id, DriverRequestBodyDto driverRequestBodyDto);

        @Operation(summary = "Delete a driver", description = "Delete a driver by id")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content(schema = @Schema(implementation = ResponseStatus.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        void deleteDriver(Long id);
}
