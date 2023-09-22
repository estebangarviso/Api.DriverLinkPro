package com.estebangarviso.driverlinkpro.domain.exception.general;

import com.estebangarviso.driverlinkpro.domain.exception.base.DomainExceptionCode;
import com.estebangarviso.driverlinkpro.domain.exception.base.DomainException;

public class BadRequestException extends DomainException {

    private BadRequestException(DomainExceptionCode code) {
        super(code);
    }

    private BadRequestException(DomainExceptionCode code, Throwable throwable) {
        super(code, throwable);
    }

    static public BadRequestException parcelIsNotLoaded() {
        return new BadRequestException(DomainExceptionCode.PARCEL_IS_NOT_LOADED, new Throwable("Package needs to be loaded before doing this action"));
    }

    static public BadRequestException userAlreadyExists() {
        return new BadRequestException(DomainExceptionCode.USER_ALREADY_EXISTS, new Throwable("User already exists"));
    }

    static public BadRequestException invalidCredentials() {
        return new BadRequestException(DomainExceptionCode.USER_INVALID_CREDENTIALS, new Throwable("Invalid email or password"));
    }

    static public BadRequestException emailNotSent(String message, Exception e) {
        return new BadRequestException(DomainExceptionCode.EMAIL_NOT_SENT, new Throwable(message, e));
    }
}
