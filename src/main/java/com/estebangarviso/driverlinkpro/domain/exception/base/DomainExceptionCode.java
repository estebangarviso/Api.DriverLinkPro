package com.estebangarviso.driverlinkpro.domain.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DomainExceptionCode {

    // Not found
    DRIVER_NOT_FOUND(201, 404, "driver_not_found"),
    VEHICLE_NOT_FOUND(202, 404, "vehicle_not_found"),
    PARCEL_NOT_FOUND(203, 404, "parcel_not_found"),
    PARCEL_DETAIL_NOT_FOUND(204, 404, "parcel_detail_not_found"),
    USER_NOT_FOUND(205, 404, "user_not_found"),
    // Bad request
    PARCEL_IS_NOT_LOADED(301, 400, "parcel_is_not_loaded"),
    USER_ALREADY_EXISTS(302, 400, "user_already_exists"),
    USER_INVALID_CREDENTIALS(303, 400, "user_invalid_credentials"),
    EMAIL_NOT_SENT(304, 400, "email_not_sent"),
    // Forbidden
    FORBIDDEN(401, 403, "forbidden"),
    USER_NOT_OWNER(402, 403, "user_not_owner");


    private final Integer codeApp;

    private final Integer statusCode;

    private final String messageKey;
}
