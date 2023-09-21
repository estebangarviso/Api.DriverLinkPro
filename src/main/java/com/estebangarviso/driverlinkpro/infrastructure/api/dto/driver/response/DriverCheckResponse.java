package com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.ParcelResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class DriverCheckResponse {
    private Boolean status;

    Set<ParcelResponse> missingParcels;
}
