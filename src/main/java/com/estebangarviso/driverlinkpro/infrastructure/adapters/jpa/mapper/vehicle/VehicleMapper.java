package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.vehicle;

import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR
)
public interface VehicleMapper {

            VehicleModel toDomain(VehicleEntity vehicleEntity);

            VehicleEntity toEntity(VehicleModel vehicleModel);

            void updateDomain(VehicleModel incomingVehicle, VehicleEntity existingVehicle);
}
