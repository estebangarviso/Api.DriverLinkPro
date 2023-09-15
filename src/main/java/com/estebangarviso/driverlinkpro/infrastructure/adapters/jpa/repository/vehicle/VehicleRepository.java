package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.vehicle;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.vehicle.VehicleEntity;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> { }
