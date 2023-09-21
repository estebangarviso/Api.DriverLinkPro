package com.estebangarviso.driverlinkpro.infrastructure.api.swagger.parcel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.*;

@Tag(name = "Parcel", description = "Parcel API")
public interface ParcelSwagger {
        @Operation(summary = "Create a new parcel", description = "Add a new parcel to the application")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = ParcelResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ParcelResponse.class)))
        })
        ParcelResponse createParcel(ParcelRequestBodyDto parcelRequestBodyDto);

        @Operation(summary = "Delete a parcel detail", description = "Delete parcel detail by parcel id and parcel detail id")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Successful operation", content = @Content(schema = @Schema(implementation = ParcelResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ParcelResponse.class)))
        })
        void deleteParcelDetail(Long parcelId, Long parcelDetailId);

        @Operation(summary = "Update a parcel status", description = "Update a parcel status by parcel id")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = ParcelResponse.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ParcelResponse.class)))
        })
        ParcelResponse updateParcelStatus(Long parcelId, ParcelStatusRequestBodyDto parcelStatusRequestBodyDto);
}
