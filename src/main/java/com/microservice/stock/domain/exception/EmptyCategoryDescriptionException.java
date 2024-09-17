package com.microservice.stock.domain.exception;

public class EmptyCategoryDescriptionException extends RuntimeException {
    public EmptyCategoryDescriptionException(String message) {
        super(message);
    }
}
