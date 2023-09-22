package com.estebangarviso.driverlinkpro.infrastructure.api.controller.parcel;

import com.estebangarviso.driverlinkpro.infrastructure.api.swagger.parcel.ParcelSwagger;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.estebangarviso.driverlinkpro.application.usecase.parcel.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response.*;
import com.estebangarviso.driverlinkpro.infrastructure.api.mapper.parcel.ParcelApiMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/parcel")
@Validated
public class ParcelController implements ParcelSwagger {
    private final CreateParcelUseCase createParcelUseCase;
    private final DeleteParcelDetailUseCase deleteParcelDetailUseCase;
    private final UpdateParcelStatusUseCase updateParcelStatusUseCase;
    private final UpdateParcelDetailUseCase updateParcelDetailUseCase;
    private final ParcelApiMapper parcelApiMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelResponse createParcel(@RequestBody ParcelRequestBodyDto parcelRequestBodyDto) {
        var parcelModel = parcelApiMapper.toDomain(parcelRequestBodyDto);
        var createdParcelModel = createParcelUseCase.createParcel(parcelModel);
        return parcelApiMapper.toResponse(createdParcelModel);
    }

    @PutMapping("/status/{parcelId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ParcelResponse updateParcelStatus(@PathVariable Long parcelId, @RequestBody ParcelStatusRequestBodyDto parcelStatusRequestBodyDto) {
        var parcelStatus = parcelStatusRequestBodyDto.getStatus();
        var updatedParcelStatus = updateParcelStatusUseCase.updateParcelStatus(parcelId, parcelStatus);
        return parcelApiMapper.toResponse(updatedParcelStatus);
    }

    @PutMapping("/detail/{parcelDetailId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ParcelDetailsResponse updateParcelDetail(@PathVariable Long parcelDetailId, @RequestBody ParcelDetailsRequestBodyDto parcelDetailsRequestBodyDto) {
        var parcelDetailsModel = parcelApiMapper.toDomain(parcelDetailsRequestBodyDto);
        var updatedParcelDetailsModel = updateParcelDetailUseCase.updateParcelDetail(parcelDetailId, parcelDetailsModel);
        return parcelApiMapper.toResponse(updatedParcelDetailsModel);
    }

    @DeleteMapping("/{parcelId}/detail/{parcelDetailId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Parcel deleted")
    public void deleteParcelDetail(@PathVariable Long parcelId, @PathVariable Long parcelDetailId) {
        deleteParcelDetailUseCase.deleteParcelDetail(parcelId, parcelDetailId);
    }
}
