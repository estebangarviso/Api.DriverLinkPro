package com.estebangarviso.driverlinkpro.application.usecase.driver;

import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;

public interface GetDriverUseCase {
    DriverModel getDriver(Long driverId);
}
