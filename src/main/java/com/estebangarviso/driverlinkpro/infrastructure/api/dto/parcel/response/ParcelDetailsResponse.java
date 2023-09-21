package com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ParcelDetailsResponse {
    @Schema(description = "Parcel's detail id", example = "1")
    private Long id;

    @Schema(description = "Parcel's detail code", example = "0570a03e-15bb-47d7-9bc1-3bf269da00fe")
    private String code;

    @Schema(description = "Parcel's detail weight", example = "1.53")
    private Double weight;

    @Schema(description = "Parcel's detail SKU", example = "SKU-1234567890")
    private String sku;

    @Schema(description = "Parcel's detail quantity", example = "1")
    private Integer quantity;

}
