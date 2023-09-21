package com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request;

import com.estebangarviso.driverlinkpro.domain.model.parcel.ParcelStatus;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ParcelDetailsRequestBodyDto.class, name = "ParcelDetailsRequestBodyDto")
})
public class ParcelRequestBodyDto {
    @Schema(description = "Parcel's scheduled date", example = "2023-09-08 23:59:59", format = "yyyy-MM-dd HH:mm:ss", type = "datetime")
    @NotEmpty(message = "Scheduled Date is required")
    private LocalDateTime scheduledDate;

    @Schema(description = "Parcel's status", implementation = ParcelStatus.class)
    @NotEmpty(message = "Status is required")
    private ParcelStatus status;

    @Schema(description = "Parcel's details", implementation = ParcelDetailsRequestBodyDto.class)
    @NotEmpty(message = "Details are required")
    private Set<ParcelDetailsRequestBodyDto> details;

    @Schema(description = "Parcel's vehicle id", example = "1")
    @NotEmpty(message = "Vehicle id is required")
    @Positive(message = "Vehicle id must be positive")
    @Pattern(regexp = "^[0-9]*$", message = "Vehicle id must be a number")
    private Long vehicleId;

}
