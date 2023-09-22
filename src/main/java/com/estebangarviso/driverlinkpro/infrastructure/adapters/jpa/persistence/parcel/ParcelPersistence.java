package com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.persistence.parcel;


import com.estebangarviso.driverlinkpro.application.usecase.parcel.*;
import com.estebangarviso.driverlinkpro.domain.exception.general.BadRequestException;
import com.estebangarviso.driverlinkpro.domain.exception.general.NotFoundException;
import com.estebangarviso.driverlinkpro.domain.model.parcel.*;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.mapper.parcel.ParcelMapper;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.parcel.ParcelDetailsRepository;
import com.estebangarviso.driverlinkpro.infrastructure.adapters.jpa.repository.parcel.ParcelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ParcelPersistence implements CreateParcelUseCase, DeleteParcelDetailUseCase, UpdateParcelStatusUseCase, UpdateParcelDetailUseCase {
    private final ParcelRepository parcelRepository;
    private final ParcelDetailsRepository parcelDetailsRepository;
    private final ParcelMapper parcelMapper;

    @Override
    public ParcelModel createParcel(ParcelModel parcel) {
        var parcelEntity = parcelMapper.toEntity(parcel);
        var savedParcelEntity = parcelRepository.save(parcelEntity);
        return parcelMapper.toDomain(savedParcelEntity);
    }

    @Override
    public void deleteParcelDetail(Long idParcel, Long idParcelDetail) {
        var parcelEntity = parcelRepository.findById(idParcel).orElseThrow(NotFoundException::parcelNotFound);
        if (!parcelEntity.getStatus().equals(ParcelStatus.LOADED)) {
            throw BadRequestException.parcelIsNotLoaded();
        }

        var parcelDetailEntity = parcelEntity.getDetails().stream()
                .filter(parcelDetail -> parcelDetail.getId().equals(idParcelDetail))
                .findFirst()
                .orElseThrow(NotFoundException::parcelDetailNotFound);
        parcelEntity.removeDetail(parcelDetailEntity);
        parcelRepository.save(parcelEntity);
    }

    @Override
    public ParcelModel updateParcelStatus(Long idParcel, ParcelStatus parcelStatus) {
        var parcelEntity = parcelRepository.findById(idParcel).orElseThrow(NotFoundException::parcelNotFound);
        parcelEntity.setStatus(parcelStatus);
        var savedParcelEntity = parcelRepository.save(parcelEntity);
        return parcelMapper.toDomain(savedParcelEntity);
    }

    @Override
    public ParcelDetailsModel updateParcelDetail(Long idParcelDetail, ParcelDetailsModel parcelDetails) {
        var parcelDetailEntity = parcelDetailsRepository.findById(idParcelDetail).orElseThrow(NotFoundException::parcelDetailNotFound);
        parcelMapper.updateDomain(parcelDetails, parcelDetailEntity);
        var savedParcelDetailEntity = parcelDetailsRepository.save(parcelDetailEntity);
        return parcelMapper.toDomain(savedParcelDetailEntity);
    }
}
