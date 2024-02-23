package com.nr.workshop.inventoryservice.exception;

public class ProductDiscontinuedException extends RuntimeException {
    public ProductDiscontinuedException(String message) {
        super(message);
    }
}