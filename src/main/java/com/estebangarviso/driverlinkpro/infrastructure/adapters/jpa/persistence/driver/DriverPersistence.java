package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.persistence.driver;

import com.estebangarviso.driverlinkpro.application.usecase.driver.*;
import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;
import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.driver.DriverRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.driver.DriverMapper;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverCheckResponse;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.ParcelResponse;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.jwt_provider.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class DriverPersistence implements
        CreateDriverUseCase, GetDriverUseCase, UpdateDriverUseCase, DeleteDriverUseCase, CheckAllDriverParcelsUseCase {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final JwtProvider jwtProvider;

    @Override
    public DriverModel createDriver(DriverModel driver) {
        DriverEntity driverEntity = driverMapper.toEntity(driver);
        var securityContext = SecurityContextHolder.getContext();
        var userEntity = (UserEntity) securityContext.getAuthentication().getPrincipal();
        driverEntity.setUser(userEntity);
        DriverEntity savedDriverEntity = driverRepository.save(driverEntity);
        return driverMapper.toDomain(savedDriverEntity);
    }

    @Override
    public DriverModel getDriver(Long idDriver) {
        DriverEntity driverEntity = jwtProvider.extractDriverByContext(idDriver);
        return driverMapper.toDomain(driverEntity);
    }

    @Override
    public DriverModel updateDriver(Long idDriver, DriverModel driver) {
        DriverEntity driverEntity = jwtProvider.extractDriverByContext(idDriver);
        driverMapper.updateDomain(driver, driverEntity);
        DriverEntity savedDriverEntity = driverRepository.save(driverEntity);
        return driverMapper.toDomain(savedDriverEntity);
    }

    @Override
    public void deleteDriver(Long idDriver) {
        DriverEntity driverEntity = jwtProvider.extractDriverByContext(idDriver);
        driverRepository.delete(driverEntity);
    }

    @Override
    public DriverCheckResponse checkAllDriverParcels(Long idDriver) {
        var driverEntity = jwtProvider.extractDriverByContext(idDriver);
        var driverVehicle = driverEntity.getVehicle();
        var vehicleParcels = driverVehicle.getParcels();
        List<ParcelStatus> parcelStatuses = List.of(ParcelStatus.DELIVERED, ParcelStatus.CANCELLED);
        var status = vehicleParcels.stream().allMatch(parcel -> parcelStatuses.contains(parcel.getStatus()));
        List<ParcelResponse> missingParcels = new ArrayList<>();
        if (!status) {
            vehicleParcels.stream()
                    .filter(parcel -> !parcelStatuses.contains(parcel.getStatus()))
                    .forEach(parcel -> missingParcels.add(ParcelResponse.builder()
                            .id(parcel.getId())
                            .status(parcel.getStatus())
                            .build()));
        }

        return DriverCheckResponse.builder()
                .status(status)
                .missingParcels(missingParcels)
                .build();
    }
}
