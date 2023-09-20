package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.driver;

import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;


@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DriverMapper {
    DriverModel toDomain(DriverEntity driverEntity);
    DriverEntity toEntity(DriverModel driverModel);
    void updateDomain(DriverModel incomingDriver, @MappingTarget DriverEntity existingDriver);
}
