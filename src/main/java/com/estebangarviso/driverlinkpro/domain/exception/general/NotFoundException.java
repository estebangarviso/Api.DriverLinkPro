package com.estebangarviso.driverlinkpro.domain.exception.general;

import com.estebangarviso.driverlinkpro.domain.exception.base.DomainExceptionCode;
import com.estebangarviso.driverlinkpro.domain.exception.base.DomainException;

public class NotFoundException extends DomainException {

        private NotFoundException(DomainExceptionCode code) {
            super(code);
        }

        private NotFoundException(DomainExceptionCode code, Throwable throwable) {
            super(code, throwable);
        }

        static public NotFoundException driverNotFound() {
            return new NotFoundException(DomainExceptionCode.DRIVER_NOT_FOUND);
        }

        static public NotFoundException vehicleNotFound() {
            return new NotFoundException(DomainExceptionCode.VEHICLE_NOT_FOUND);
        }

        static public NotFoundException addressNotFound() {
            return new NotFoundException(DomainExceptionCode.ADDRESS_NOT_FOUND);
        }

        static public NotFoundException packageNotFound() {
            return new NotFoundException(DomainExceptionCode.PARCEL_NOT_FOUND);
        }

        static public NotFoundException packageDetailsNotFound() {
            return new NotFoundException(DomainExceptionCode.PARCEL_DETAILS_NOT_FOUND);
        }

        static public NotFoundException userNotFound() {
            return new NotFoundException(DomainExceptionCode.USER_NOT_FOUND);
        }
}
