package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.persistance.driver;

import com.estebangarviso.driverlinkpro.application.usecase.driver.*;
import com.estebangarviso.driverlinkpro.domain.exception.general.NotFoundException;
import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.driver.DriverRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.driver.DriverMapper;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.UserEntity;
import com.estebangarviso.driverlinkpro.infrastructure.authentication.jwt_provider.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DriverPersistence implements
        CreateDriverUseCase, GetDriverUseCase, UpdateDriverUseCase, DeleteDriverUseCase {
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
    public DriverModel getDriver(Long driverId) {
        DriverEntity driverEntity = jwtProvider.getDriverBySecurityContext(driverId);
        return driverMapper.toDomain(driverEntity);
    }

    @Override
    public DriverModel updateDriver(Long driverId, DriverModel driver) {
        DriverEntity driverEntity = jwtProvider.getDriverBySecurityContext(driverId);
        driverMapper.updateDomain(driver, driverEntity);
        DriverEntity savedDriverEntity = driverRepository.save(driverEntity);
        return driverMapper.toDomain(savedDriverEntity);
    }

    @Override
    public void deleteDriver(Long driverId) {
        DriverEntity driverEntity = jwtProvider.getDriverBySecurityContext(driverId);
        driverRepository.delete(driverEntity);
    }
}
