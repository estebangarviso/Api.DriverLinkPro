package com.estebangarviso.driverlinkpro.infrastructure.api.handler;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.estebangarviso.driverlinkpro.infrastructure.api.dto.error.ErrorResponse;
import com.estebangarviso.driverlinkpro.domain.exception.base.DomainException;

import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class HandlerController {

    private final MessageSource messageSource;

    private final Logger logger = LoggerFactory.getLogger(HandlerController.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> domainError(DomainException exception) {

        logger.warn("Domain Error found: ", exception);

        return buildResponseWithDomainException(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generalError(Exception exception) {

        logger.error("Critical Error found: ", exception);

        return buildResponse(100, HttpStatus.INTERNAL_SERVER_ERROR, "critical_error", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception) {

        logger.error("Validation Error found: ", exception);

        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return buildResponse(101, HttpStatus.BAD_REQUEST, "validation_error", errors);
    }

    private ResponseEntity<ErrorResponse> buildResponse(Integer codeAppError, HttpStatus httpStatus, String messageKey, Object detail) {

        var errorResponse = ErrorResponse.builder()
                .codeAppError(codeAppError)
                .httpStatus(httpStatus.value())
                .message(getMessage(messageKey))
                .detailMessage(detail)
                .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }


    private ResponseEntity<ErrorResponse> buildResponseWithDomainException(DomainException exception) {

        var httpStatusFound = HttpStatus.valueOf(exception.getCode().getStatusCode());

        var errorResponse = ErrorResponse.builder()
                .codeAppError(exception.getCode().getCodeApp())
                .httpStatus(httpStatusFound.value())
                .message(getMessage(exception.getCode().getMessageKey()))
                .detailMessage(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, httpStatusFound);
    }

    private String getMessage(String messageKey) {

        return messageSource.getMessage(messageKey, null, Locale.getDefault());
    }
}
