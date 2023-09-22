package com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.response.VehicleResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class ParcelResponse {
    @Schema(description = "Parcel's id", example = "1")
    private Long id;

    @Schema(description = "Parcel's code", example = "ba48ebcc-4566-4a0f-be62-fb83567fbeee")
    private String code;

    @Schema(description = "Parcel's scheduled date", example = "2023-09-08 23:59:59", format = "yyyy-MM-dd HH:mm:ss", type = "datetime")
    private LocalDateTime scheduledDate;

    @Schema(description = "Parcel's status", implementation = ParcelStatus.class)
    private ParcelStatus status;

    @Schema(description = "Parcel's weight", example = "4.59")
    private Double weight;

    @Schema(description = "Parcel's vehicle", implementation = VehicleResponse.class)
    private VehicleResponse vehicle;

    @Schema(description = "Parcel's details", implementation = ParcelDetailsResponse.class)
    private List<ParcelDetailsResponse> details;
}
