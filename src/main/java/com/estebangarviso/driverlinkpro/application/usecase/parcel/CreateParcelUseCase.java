package com.estebangarviso.driverlinkpro.application.usecase.parcel;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelModel;
public interface CreateParcelUseCase {
    ParcelModel createParcel(ParcelModel parcel);
}
