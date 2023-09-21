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

        static public NotFoundException parcelNotFound() {
            return new NotFoundException(DomainExceptionCode.PARCEL_NOT_FOUND);
        }

        static public NotFoundException parcelDetailNotFound() {
            return new NotFoundException(DomainExceptionCode.PARCEL_DETAIL_NOT_FOUND);
        }

        static public NotFoundException userNotFound() {
            return new NotFoundException(DomainExceptionCode.USER_NOT_FOUND);
        }
}
