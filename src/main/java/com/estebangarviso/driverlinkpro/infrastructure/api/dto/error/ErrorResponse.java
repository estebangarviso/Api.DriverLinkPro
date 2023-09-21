package com.estebangarviso.driverlinkpro.infrastructure.api.dto.error;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private Integer codeAppError;

    private Integer httpStatus;

    private String message;

    private Object detailMessage;
}
