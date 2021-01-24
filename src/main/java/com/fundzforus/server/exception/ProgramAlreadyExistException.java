package com.fundzforus.server.exception;

public class ProgramAlreadyExistException extends RuntimeException {
    public ProgramAlreadyExistException(String message) {
        super(message);
    }
}
