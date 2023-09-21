package com.estebangarviso.driverlinkpro.infrastructure.api.dto.parcel.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ParcelDetailsRequestBodyDto {
    @Schema(description = "Parcel Details' weight", example = "1.53")
    @NotEmpty(message = "Weight is required")
    @DecimalMin(value = "0.01", message = "Weight must be greater than 0")
    @DecimalMax(value = "99999999999999999999.99", message = "Weight must be less than 99999999999999999999.99")
    @PositiveOrZero(message = "Weight must be greater than or equal to 0")
    @Pattern(regexp = "^[0-9]{1,20}(\\.[0-9]{1,2})?$", message = "Weight must be a number with a maximum of 2 decimals")
    private Double weight;

    @Schema(description = "Parcel Details' SKU", example = "SKU-1234567890")
    @NotEmpty(message = "SKU is required")
    @Size(max = 64, message = "SKU must be a maximum of 64 characters")
    @Size(min = 1, message = "SKU must be a minimum of 1 character")
    private String sku;

    @Schema(description = "Parcel Details' quantity", example = "1")
    @NotEmpty(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be greater than or equal to 0")
    @Pattern(regexp = "^[0-9]*$", message = "Vehicle id must be a number")
    private Integer quantity;

    @Schema(description = "Parcel Details' parcel id", example = "1")
    @NotEmpty(message = "Parcel id is required")
    @Positive(message = "Parcel id must be positive")
    @Pattern(regexp = "^[0-9]*$", message = "Parcel id must be a number")
    private Long parcelId;

}
