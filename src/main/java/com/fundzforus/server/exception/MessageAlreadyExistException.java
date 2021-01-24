package com.fundzforus.server.exception;

public class MessageAlreadyExistException extends RuntimeException {
    public MessageAlreadyExistException(String message) {
        super(message);
    }
}
