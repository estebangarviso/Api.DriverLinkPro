package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.listener;

import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.entity.driver.DriverEntity;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.driver.DriverRepository;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DriverListener {
    private static DriverRepository driverRepository;

    @Autowired
    public void setDriverRepository(DriverRepository driverRepository) {
        DriverListener.driverRepository = driverRepository;
    }

    @PrePersist
    public void onPreInsert(DriverEntity driverEntity) {
        if (driverEntity.getCode() == null) {
            var i = 0L;

            var driverFound = driverRepository.findFirstByCodeNotNullOrderByCodeDesc();

            if (driverFound.isPresent() && Objects.nonNull(driverFound.get().getCode())) {
                i = Long.parseLong(driverFound.get().getCode().substring(2));
            }

            i++;

            driverEntity.setCode("D-" + String.format("%06d", i));
        }
    }
}
