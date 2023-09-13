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

    static public BadRequestException packageIsNotLoaded() {
        return new BadRequestException(DomainExceptionCode.PARCEL_IS_NOT_LOADED, new Throwable("Package needs to be loaded before doing this action"));
    }

    static public BadRequestException packageCannotBeChanged(String message) {
        return new BadRequestException(DomainExceptionCode.PARCEL_CANNOT_BE_CHANGED, new Throwable(message));
    }
}
