package com.metroservice.metro.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MetroException extends RuntimeException {
    private final HttpStatus status;

    public MetroException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}