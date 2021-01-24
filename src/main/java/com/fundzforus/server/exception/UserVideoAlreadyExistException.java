package com.fundzforus.server.exception;

public class UserVideoAlreadyExistException extends RuntimeException {
    public UserVideoAlreadyExistException(String message) {
        super(message);
    }
}
