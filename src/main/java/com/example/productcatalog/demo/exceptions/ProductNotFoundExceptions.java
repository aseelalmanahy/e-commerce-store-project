package com.example.productcatalog.demo.exceptions;

public class ProductNotFoundExceptions extends RuntimeException{

    public ProductNotFoundExceptions(String message){
        super(message);
    }
}
