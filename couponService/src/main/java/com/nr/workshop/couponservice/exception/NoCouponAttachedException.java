package com.nr.workshop.couponservice.exception;

public class NoCouponAttachedException extends RuntimeException {
    public NoCouponAttachedException(String message) {
        super(message);
    }
}