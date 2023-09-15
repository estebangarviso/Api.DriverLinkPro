package com.estebangarviso.driverlinkpro.domain.model.driver;

import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DriverModel implements SoftDeleteInterface, EnableInterface {
    private Long id;
    private String name;
    private String cellphone;
    private String email;
    private Boolean isEnabled;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private VehicleModel vehicle;
}
