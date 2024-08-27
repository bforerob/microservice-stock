package com.microservice.stock.configuration.exceptionhandler;

import com.microservice.stock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.microservice.stock.configuration.Constants;
import com.microservice.stock.domain.exception.EmptyFieldException;
import com.microservice.stock.domain.exception.LengthFieldException;
import com.microservice.stock.domain.util.DomainConstants;
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


    @ExceptionHandler(LengthFieldException.class)
    public ResponseEntity<ExceptionResponse> handleLengthFieldException(LengthFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.LENGTH_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleSupplierAlreadyExistsException(CategoryAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(Constants.CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

}
