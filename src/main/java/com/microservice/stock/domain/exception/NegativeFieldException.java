package com.microservice.stock.domain.exception;

public class NegativeFieldException extends RuntimeException {
    public NegativeFieldException(String message) {
        super(message);
    }
}
