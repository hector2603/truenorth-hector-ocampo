package com.truenorth.truenorth.application.exception;

public class TecnicalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TecnicalException(String message) {
        super(message);
    }

    public TecnicalException(String message, Exception e) {
        super(message, e);
    }
}
