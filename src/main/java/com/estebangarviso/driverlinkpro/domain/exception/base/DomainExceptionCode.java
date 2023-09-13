package com.estebangarviso.driverlinkpro.domain.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DomainExceptionCode {

    // Not fund
    DRIVER_NOT_FOUND(201, 404, "driver_not_found"),
    VEHICLE_NOT_FOUND(202, 404, "vehicle_not_found"),
    ADDRESS_NOT_FOUND(203, 404, "address_not_found"),
    PARCEL_NOT_FOUND(204, 404, "parcel_not_found"),
    PARCEL_ARTICLES_NOT_FOUND(205, 404, "parcel_articles_not_found"),
    USER_NOT_FOUND(206, 404, "user_not_found"),
    // Bad request
    PARCEL_IS_NOT_LOADED(301, 400, "parcel_is_not_loaded"),
    PARCEL_CANNOT_BE_CHANGED(302, 400, "parcel_cannot_be_changed");

    private final Integer codeApp;

    private final Integer statusCode;

    private final String messageKey;
}
