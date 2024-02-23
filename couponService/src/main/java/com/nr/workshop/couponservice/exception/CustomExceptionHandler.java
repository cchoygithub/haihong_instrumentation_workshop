package com.nr.workshop.couponservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidCouponException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> invalidCouponException(InvalidCouponException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }
    @ExceptionHandler(NoCouponOfferException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> noCouponOfferException(NoCouponOfferException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }
    @ExceptionHandler(NoCouponAttachedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> noCouponOfferException(NoCouponAttachedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GONE);
    }


}
