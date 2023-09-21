package com.estebangarviso.driverlinkpro.infrastructure.api.mapper.parcel;


import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.*;
import com.estebangarviso.driverlinkpro.domain.model.parcel.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ParcelApiMapper {
    @Mapping(target = "vehicle.id", source = "vehicleId")
    ParcelModel toDomain(ParcelRequestBodyDto parcelRequestBodyDto);
    ParcelResponse toResponse(ParcelModel parcelModel);

    ParcelDetailsModel toDomain(ParcelDetailsRequestBodyDto parcelDetailsRequestBodyDto);

    ParcelDetailsResponse toResponse(ParcelDetailsModel parcelDetailsModel);
}
