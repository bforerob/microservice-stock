package com.microservice.stock.configuration.exceptionhandler;

import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageNumberException;
import com.microservice.stock.adapters.driven.jpa.mysql.exception.NegativePageSizeException;
import com.microservice.stock.domain.exception.*;
import com.microservice.stock.configuration.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.EMPTY_FIELD_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<ExceptionResponse> handleNullFieldException(NullFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.NULL_FIELD_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(LengthFieldException.class)
    public ResponseEntity<ExceptionResponse> handleLengthFieldException(LengthFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.LENGTH_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleSupplierAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NegativePageNumberException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPageNumberException(NegativePageNumberException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                Constants.NEGATIVE_PAGE_NUMBER_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidSortDirectionException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidSortDirectionException(InvalidSortDirectionException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_SORT_DIRECTION_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidSortParameterException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidSortParameterException(InvalidSortParameterException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_SORT_PARAMETER_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NegativePageSizeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPageSizeException(NegativePageSizeException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.NEGATIVE_PAGE_SIZE_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

}
