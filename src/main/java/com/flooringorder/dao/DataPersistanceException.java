package com.flooringorder.dao;

public class DataPersistanceException extends Exception {

    public DataPersistanceException(String message) {
        super(message);
    }

    public DataPersistanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
