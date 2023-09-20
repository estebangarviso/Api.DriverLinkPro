package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.driver;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface DriverRepository extends CrudRepository<DriverEntity, Long> {
    Optional<DriverEntity> findFirstByCodeNotNullOrderByCodeDesc();

}
