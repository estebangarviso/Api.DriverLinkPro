package com.estebangarviso.driverlinkpro.domain.model.vehicle;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import java.time.LocalDateTime;

import com.estebangarviso.driverlinkpro.domain.model.driver.DriverModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleModel implements SoftDeleteInterface {
    private Long id;
    private String code;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private DriverModel driver;
}
