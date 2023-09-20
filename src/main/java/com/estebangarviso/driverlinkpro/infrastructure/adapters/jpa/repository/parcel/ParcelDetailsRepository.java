package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelDetailsRepository extends JpaRepository<ParcelDetailsEntity, Long> { }
