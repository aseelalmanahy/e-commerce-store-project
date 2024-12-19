package com.example.productcatalog.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundExceptions.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundExceptions exceptions){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptions.getMessage());
    }
}
