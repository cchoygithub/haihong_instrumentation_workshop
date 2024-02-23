package com.nr.workshop.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ProductDiscontinuedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<String> handleProductDiscontinuedException(ProductDiscontinuedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }
    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> handleOutOfStockException(OutOfStockException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }
}
