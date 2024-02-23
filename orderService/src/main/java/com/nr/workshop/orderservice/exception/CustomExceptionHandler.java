package com.nr.workshop.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OrderUnsuccessfulException410.class)
//    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> handleOutOfStockException(OrderUnsuccessfulException410 ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }

    @ExceptionHandler(OrderUnsuccessfulException501.class)
//    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<String> handleOutOfStockException(OrderUnsuccessfulException501 ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_IMPLEMENTED);
    }
}
