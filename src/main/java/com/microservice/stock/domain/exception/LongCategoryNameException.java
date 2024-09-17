package com.microservice.stock.domain.exception;

public class LongCategoryNameException extends RuntimeException {
    public LongCategoryNameException(String message) {
        super(message);
    }
}
