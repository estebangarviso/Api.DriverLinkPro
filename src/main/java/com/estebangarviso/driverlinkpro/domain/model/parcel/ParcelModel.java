package com.estebangarviso.driverlinkpro.domain.model.parcel;

import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ParcelModel implements SoftDeleteInterface, EnableInterface {
    private Long id;
    private String code;
    private String scheduledDate;
    private String status;
    private Double weight;
    private Boolean isEnabled;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private VehicleModel vehicle;
    private Set<ParcelDetailsModel> parcelDetails;
}
