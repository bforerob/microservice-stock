package com.microservice.stock.domain.exception;

public class LengthFieldException extends RuntimeException {
    public LengthFieldException(String message) {
        super(message);
    }
}
