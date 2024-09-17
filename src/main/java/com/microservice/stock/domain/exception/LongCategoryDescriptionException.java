package com.microservice.stock.domain.exception;

public class LongCategoryDescriptionException extends RuntimeException {
    public LongCategoryDescriptionException(String message) {
        super(message);
    }
}
