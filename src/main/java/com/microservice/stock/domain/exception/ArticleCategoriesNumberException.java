package com.microservice.stock.domain.exception;

public class ArticleCategoriesNumberException extends RuntimeException {
    public ArticleCategoriesNumberException(String message) {
        super(message);
    }
}
