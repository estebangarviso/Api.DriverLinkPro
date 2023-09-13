package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.driver;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.Driver;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> { }
