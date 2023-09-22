package com.estebangarviso.driverlinkpro.application.usecase.driver;

import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;

public interface UpdateDriverUseCase {
    DriverModel updateDriver(Long idDriver, DriverModel driver);
}
