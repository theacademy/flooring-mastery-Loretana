package com.flooringorder.service;

public class InvalidOrderInformationException extends Exception {

    public InvalidOrderInformationException(String message) {
        super(message);
    }

    public InvalidOrderInformationException(String message, Throwable cause) {
        super(message, cause);
    }
}
