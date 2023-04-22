package com.example.ecommerce.product;

public class ProductNotFoundByIDException extends RuntimeException{
    public ProductNotFoundByIDException(String message){
        super(message);
    }
}
