package com.flooringorder.service;

public class InvalidTaxInformationException extends Exception {

    public InvalidTaxInformationException(String message) {
        super(message);
    }

    public InvalidTaxInformationException(String message, Throwable cause) {
        super(message, cause);
    }
}
