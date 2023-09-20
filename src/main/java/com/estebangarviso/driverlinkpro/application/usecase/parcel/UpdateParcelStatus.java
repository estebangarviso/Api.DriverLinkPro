package com.estebangarviso.driverlinkpro.application.usecase.parcel;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
public interface UpdateParcelStatus {
    ParcelStatus updateParcelStatus(Long parcelId, ParcelStatus parcelStatus);
}
