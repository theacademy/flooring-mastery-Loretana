package com.flooringorder.service;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
