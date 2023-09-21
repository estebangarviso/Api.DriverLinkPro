package com.estebangarviso.driverlinkpro.application.usecase.driver;

import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverCheckResponse;

public interface CheckAllDriverParcelsUseCase {
    DriverCheckResponse checkAllDriverParcels(Long driverId);
}
