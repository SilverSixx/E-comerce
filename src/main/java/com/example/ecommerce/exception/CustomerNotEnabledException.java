package com.example.ecommerce.exception;

public class CustomerNotEnabledException extends RuntimeException {
    public CustomerNotEnabledException(String message) {
        super(message);
    }
}

