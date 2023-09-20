package com.estebangarviso.driverlinkpro.application.usecase.parcel;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelDetailsModel;

public interface DeleteParcelDetailsUseCase {
    ParcelDetailsModel deleteParcelDetails(Long parcelDetailsId);
}
