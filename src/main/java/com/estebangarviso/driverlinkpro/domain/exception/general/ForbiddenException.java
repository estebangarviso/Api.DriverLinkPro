package com.estebangarviso.driverlinkpro.domain.exception.general;

import com.estebangarviso.driverlinkpro.domain.exception.base.DomainExceptionCode;
import com.estebangarviso.driverlinkpro.domain.exception.base.DomainException;

public class ForbiddenException extends DomainException{

        private ForbiddenException(DomainExceptionCode code) {
            super(code);
        }

        private ForbiddenException(DomainExceptionCode code, Throwable throwable) {
            super(code, throwable);
        }

        static public ForbiddenException common() {
            return new ForbiddenException(DomainExceptionCode.FORBIDDEN);
        }

        static public ForbiddenException userNotOwner() {
            return new ForbiddenException(DomainExceptionCode.USER_NOT_OWNER);
        }
}
