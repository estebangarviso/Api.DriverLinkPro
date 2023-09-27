package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.*;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Parcel", description = "Parcel API")
public interface ParcelSwagger {
        @Operation(summary = "Create a new parcel", description = "Create a new parcel")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = ParcelResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        ParcelResponse createParcel(ParcelRequestBodyDto parcelRequestBodyDto);

        @Operation(summary = "Update a parcel detail", description = "Update a parcel detail by parcel detail id")
        @ApiResponses({
                @ApiResponse(responseCode = "202", description = "Successful operation", content = @Content(schema = @Schema(implementation = ParcelDetailsResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        ParcelDetailsResponse updateParcelDetail(Long idParcelDetail, ParcelDetailsRequestBodyDto parcelDetailsRequestBodyDto);

        @Operation(summary = "Update a parcel status", description = "Update a parcel status by parcel id")
        @ApiResponses({
                @ApiResponse(responseCode = "202", description = "Successful operation", content = @Content(schema = @Schema(implementation = ParcelResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        ParcelResponse updateParcelStatus(Long idParcel, ParcelStatusRequestBodyDto parcelStatusRequestBodyDto);

        @Operation(summary = "Delete a parcel detail", description = "Delete parcel detail by parcel id and parcel detail id")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content(schema = @Schema(implementation = ResponseStatus.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        void deleteParcelDetail(Long idParcel, Long idParcelDetail);
}
