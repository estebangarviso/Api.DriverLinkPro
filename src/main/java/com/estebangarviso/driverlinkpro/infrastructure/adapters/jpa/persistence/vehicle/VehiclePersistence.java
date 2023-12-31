package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.persistence.vehicle;

import com.estebangarviso.driverlinkpro.application.usecase.vehicle.CreateVehicleUseCase;
import com.estebangarviso.driverlinkpro.application.usecase.vehicle.DeleteVehicleUseCase;
import com.estebangarviso.driverlinkpro.application.usecase.vehicle.GetVehicleUseCase;
import com.estebangarviso.driverlinkpro.application.usecase.vehicle.UpdateVehicleUseCase;
import com.estebangarviso.driverlinkpro.domain.exception.general.NotFoundException;
import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.vehicle.VehicleMapper;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.vehicle.VehicleRepository;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.jwt_provider.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class VehiclePersistence implements
        CreateVehicleUseCase, GetVehicleUseCase, UpdateVehicleUseCase, DeleteVehicleUseCase {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final JwtProvider jwtProvider;

    @Override
    public VehicleModel createVehicle(VehicleModel vehicle) {
        VehicleEntity vehicleEntity = vehicleMapper.toEntity(vehicle);
        var idDriver = vehicle.getDriver().getId();
        var driverEntity = jwtProvider.extractDriverByContext(idDriver);
        vehicleEntity.setDriver(driverEntity);
        VehicleEntity savedVehicleEntity = vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toDomain(savedVehicleEntity);
    }

    @Override
    public VehicleModel getVehicle(Long idVehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(idVehicle).orElseThrow(NotFoundException::vehicleNotFound);
        return vehicleMapper.toDomain(vehicleEntity);
    }

    @Override
    public VehicleModel updateVehicle(Long idVehicle, VehicleModel vehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(idVehicle).orElseThrow(NotFoundException::vehicleNotFound);
        vehicleMapper.updateDomain(vehicle, vehicleEntity);
        VehicleEntity savedVehicleEntity = vehicleRepository.save(vehicleEntity);
        return vehicleMapper.toDomain(savedVehicleEntity);
    }

    @Override
    public void deleteVehicle(Long idVehicle) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(idVehicle).orElseThrow(NotFoundException::vehicleNotFound);
        vehicleRepository.delete(vehicleEntity);
    }
}
