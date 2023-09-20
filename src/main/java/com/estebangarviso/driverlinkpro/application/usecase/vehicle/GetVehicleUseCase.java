package com.estebangarviso.driverlinkpro.application.usecase.vehicle;

import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;

public interface GetVehicleUseCase {
    VehicleModel getVehicle(Long vehicleId);
}
