package com.springboot.spring_security.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("product " + id + " is not found");
    }
    
}
