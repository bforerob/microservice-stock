package com.microservice.stock.domain.exception;

public class InvalidSortDirectionException extends RuntimeException {
    public InvalidSortDirectionException(String message) {
        super(message);
    }
}
