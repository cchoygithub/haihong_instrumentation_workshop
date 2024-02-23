package com.nr.workshop.couponservice.exception;

public class NoCouponOfferException extends RuntimeException {
    public NoCouponOfferException(String message) {
        super(message);
    }
}