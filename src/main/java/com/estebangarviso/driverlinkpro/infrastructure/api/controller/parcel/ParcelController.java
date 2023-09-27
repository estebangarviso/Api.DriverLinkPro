package com.estebangarviso.driverlinkpro.infrastructure.api.controller.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.parcel.ParcelSwagger;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.estebangarviso.driverlinkpro.application.usecase.parcel.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.mapper.parcel.ParcelApiMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/parcel")
public class ParcelController implements ParcelSwagger {
    private final CreateParcelUseCase createParcelUseCase;
    private final DeleteParcelDetailUseCase deleteParcelDetailUseCase;
    private final UpdateParcelStatusUseCase updateParcelStatusUseCase;
    private final UpdateParcelDetailUseCase updateParcelDetailUseCase;
    private final ParcelApiMapper parcelApiMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelResponse createParcel(@RequestBody @Valid ParcelRequestBodyDto parcelRequestBodyDto) {
        var parcelModel = parcelApiMapper.toDomain(parcelRequestBodyDto);
        var createdParcelModel = createParcelUseCase.createParcel(parcelModel);
        return parcelApiMapper.toResponse(createdParcelModel);
    }

    @PutMapping("/status/{idParcel}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ParcelResponse updateParcelStatus(@PathVariable Long idParcel, @RequestBody @Valid ParcelStatusRequestBodyDto parcelStatusRequestBodyDto) {
        var parcelStatus = parcelStatusRequestBodyDto.getStatus();
        var updatedParcelStatus = updateParcelStatusUseCase.updateParcelStatus(idParcel, parcelStatus);
        return parcelApiMapper.toResponse(updatedParcelStatus);
    }

    @PutMapping("/detail/{idParcelDetail}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ParcelDetailsResponse updateParcelDetail(@PathVariable Long idParcelDetail, @RequestBody @Valid ParcelDetailsRequestBodyDto parcelDetailsRequestBodyDto) {
        var parcelDetailsModel = parcelApiMapper.toDomain(parcelDetailsRequestBodyDto);
        var updatedParcelDetailsModel = updateParcelDetailUseCase.updateParcelDetail(idParcelDetail, parcelDetailsModel);
        return parcelApiMapper.toResponse(updatedParcelDetailsModel);
    }

    @DeleteMapping("/{idParcel}/detail/{idParcelDetail}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Parcel deleted")
    public void deleteParcelDetail(@PathVariable Long idParcel, @PathVariable Long idParcelDetail) {
        deleteParcelDetailUseCase.deleteParcelDetail(idParcel, idParcelDetail);
    }
}
