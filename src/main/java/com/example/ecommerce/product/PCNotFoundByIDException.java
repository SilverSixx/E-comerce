package com.example.ecommerce.product;

public class PCNotFoundByIDException extends RuntimeException{
    public PCNotFoundByIDException(String message){
        super(message);
    }
}
