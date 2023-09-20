package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.parcel;
import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelDetailsModel;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelDetailsEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.ERROR
)
public interface ParcelDetailsMapper {

            ParcelDetailsModel toDomain(ParcelDetailsEntity parcelDetailsEntity);

            ParcelDetailsEntity toEntity(ParcelDetailsModel parcelDetailsModel);

            void updateDomain(ParcelDetailsModel incomingParcelDetails, ParcelDetailsEntity existingParcelDetails);
}
