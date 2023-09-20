package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.parcel;

import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR
)
public interface ParcelMapper {

        ParcelModel toDomain(ParcelEntity parcelEntity);

        ParcelEntity toEntity(ParcelModel parcelModel);

        void updateDomain(ParcelModel incomingParcel, ParcelEntity existingParcel);
}
