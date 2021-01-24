package com.fundzforus.server.exception;

public class UserVideoNotFoundException extends RuntimeException {
    public UserVideoNotFoundException(String message) {
        super(message);
    }
}
