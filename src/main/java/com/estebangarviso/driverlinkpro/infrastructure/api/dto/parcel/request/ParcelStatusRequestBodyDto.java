package com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParcelStatusRequestBodyDto {
    @Schema(description = "Parcel's status", implementation = ParcelStatus.class)
    private ParcelStatus status;
}
