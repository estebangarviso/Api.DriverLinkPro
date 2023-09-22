package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.admin;

import org.mapstruct.*;
import com.estebangarviso.driverlinkpro.domain.model.user.*;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.user.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AdminMapper {
    UserModel toDomain(UserEntity userEntities);
    UserEntity toEntity(UserModel userModel);
}
