package com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParcelRequestDto {
    @Schema(description = "Parcel's Scheduled Date", example = "2023-09-08 23:59:59", format = "yyyy-MM-dd HH:mm:ss", type = "datetime")
    @NotEmpty(message = "Scheduled Date is required")
    private String scheduledDate;

    @Schema(description = "Parcel's Status", implementation = ParcelStatus.class)
    @NotEmpty(message = "Status is required")
    private ParcelStatus status;

}
