package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.parcel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.parcel.ParcelEntity;

@Repository
public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> { }
