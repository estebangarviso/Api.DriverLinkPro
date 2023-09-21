package com.estebangarviso.driverlinkpro.domain.model.parcel;

import com.estebangarviso.driverlinkpro.domain.common.SoftDeleteInterface;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParcelDetailsModel implements SoftDeleteInterface {
    private Long id;
    private String code;
    private Double weight;
    private String sku;
    private Integer quantity;
    private Long parcelId;
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
}
