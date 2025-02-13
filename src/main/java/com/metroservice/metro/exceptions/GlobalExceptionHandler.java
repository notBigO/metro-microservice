package com.metroservice.metro.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MetroException.class)
    public ResponseEntity<ErrorResponse> handleMetroException(MetroException ex) {
        log.error("Metro exception occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("An unexpected error occurred"));
    }
}