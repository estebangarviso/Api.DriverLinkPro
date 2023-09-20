package com.estebangarviso.driverlinkpro.infrastructure.api.mapper.vehicle;


import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.request.VehicleRequestDto;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.vehicle.response.VehicleResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface VehicleApiMapper {
    VehicleModel toDomain(VehicleRequestDto vehicleRequestDto);
    VehicleResponse toResponse(VehicleModel vehicleModel);
}
