package com.estebangarviso.driverlinkpro.infrastructure.api.mapper.driver;


import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.request.DriverRequestBodyDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.driver.response.DriverResponse;
import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DriverApiMapper {
    DriverModel toDomain(DriverRequestBodyDto driverRequestBodyDto);
    DriverResponse toResponse(DriverModel driverModel);
}
