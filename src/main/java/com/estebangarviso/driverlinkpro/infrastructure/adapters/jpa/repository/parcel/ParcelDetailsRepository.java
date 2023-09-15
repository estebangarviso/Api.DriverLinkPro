package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelDetailsRepository extends JpaRepository<ParcelDetailsEntity, Long> { }
