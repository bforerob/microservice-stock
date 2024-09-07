package com.microservice.stock.domain.exception;

public class InvalidSortParameterException extends RuntimeException {
    public InvalidSortParameterException(String message) {
        super(message);
    }
}
