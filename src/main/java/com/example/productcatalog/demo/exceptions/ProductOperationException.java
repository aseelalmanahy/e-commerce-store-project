package com.example.productcatalog.demo.exceptions;

public class ProductOperationException extends RuntimeException{

    public ProductOperationException(String message, Exception e) {
        super(message, e);
    }
}
