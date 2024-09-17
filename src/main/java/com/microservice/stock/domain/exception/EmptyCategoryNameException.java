package com.microservice.stock.domain.exception;

public class EmptyCategoryNameException extends RuntimeException {
  public EmptyCategoryNameException(String message) {
    super(message);
  }
}
