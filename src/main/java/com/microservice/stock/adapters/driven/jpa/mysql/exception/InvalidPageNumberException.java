package com.microservice.stock.adapters.driven.jpa.mysql.exception;

public class InvalidPageNumberException extends RuntimeException {
    public InvalidPageNumberException(String message) {
        super(message);
    }
}
