package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.parcel;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelDetailsModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelDetailsEntity;
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

        ParcelDetailsModel toDomain(ParcelDetailsEntity parcelDetailsEntity);

        ParcelEntity toEntity(ParcelModel parcelModel);

        void updateDomain(ParcelModel incomingParcel, @MappingTarget ParcelEntity existingParcel);

        void updateDomain(ParcelDetailsModel incomingParcelDetails, @MappingTarget ParcelDetailsEntity existingParcelDetails);
}
