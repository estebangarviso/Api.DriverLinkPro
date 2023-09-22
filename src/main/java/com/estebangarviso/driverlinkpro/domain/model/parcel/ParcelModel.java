package com.estebangarviso.driverlinkpro.domain.model.parcel;

import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelModel implements SoftDeleteInterface, EnableInterface {
    private Long id;
    private String code;
    private String scheduledDate;
    private ParcelStatus status;
    private Double weight;

    private Boolean isEnabled;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    private VehicleModel vehicle;

    private List<ParcelDetailsModel> details;

}
