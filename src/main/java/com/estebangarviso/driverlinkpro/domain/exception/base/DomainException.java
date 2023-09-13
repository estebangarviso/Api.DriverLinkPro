package com.estebangarviso.driverlinkpro.domain.exception.base;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException{
    private final DomainExceptionCode code;

    protected DomainException(DomainExceptionCode code) {

        this.code = code;
    }

    protected DomainException(DomainExceptionCode code, Throwable throwable) {

        super(throwable);
        this.code = code;
    }

}
