package com.anupam.eventManagement.exception;

public class EmailIdPresentException extends RuntimeException {
    public EmailIdPresentException(String message) {
        super(message);
    }
}
