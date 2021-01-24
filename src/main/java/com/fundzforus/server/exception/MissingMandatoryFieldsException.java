package com.fundzforus.server.exception;

public class MissingMandatoryFieldsException extends RuntimeException {
    public MissingMandatoryFieldsException(String message) {
        super(message);
    }
}
