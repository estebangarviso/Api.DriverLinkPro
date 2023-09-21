package com.estebangarviso.driverlinkpro.application.usecase.parcel;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelModel;

public interface UpdateParcelStatusUseCase {
    ParcelModel updateParcelStatus(Long parcelId, ParcelStatus parcelStatus);
}
