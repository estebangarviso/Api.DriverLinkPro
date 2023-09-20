package com.estebangarviso.driverlinkpro.domain.model.parcel;

import com.estebangarviso.driverlinkpro.domain.common.EnableInterface;
import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import com.estebangarviso.driverlinkpro.domain.model.vehicle.VehicleModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Setter(AccessLevel.NONE)
    private Set<ParcelDetailsModel> parcelDetails  = new HashSet<>();

    public void addParcelDetails(ParcelDetailsModel parcelDetailsModel) {
        this.parcelDetails.add(parcelDetailsModel);
        this.weight += parcelDetailsModel.getWeight();
    }

    public void removeParcelDetails(ParcelDetailsModel parcelDetailsModel) {
        this.parcelDetails.remove(parcelDetailsModel);
        this.weight -= parcelDetailsModel.getWeight();
    }
}
