package com.example.ecommerce.exception;

public class CustomerWithIDNotFound extends RuntimeException{
    public CustomerWithIDNotFound(String message){
        super(message);
    }
}
