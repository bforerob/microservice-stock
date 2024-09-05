package com.microservice.stock.adapters.driven.jpa.mysql.exception;

public class InvalidPageSizeException extends RuntimeException {
    public InvalidPageSizeException(String message) {
        super(message);
    }
}
