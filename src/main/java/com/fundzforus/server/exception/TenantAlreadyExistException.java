package com.fundzforus.server.exception;

public class TenantAlreadyExistException extends RuntimeException {
    public TenantAlreadyExistException(String message) {
        super(message);
    }
}
