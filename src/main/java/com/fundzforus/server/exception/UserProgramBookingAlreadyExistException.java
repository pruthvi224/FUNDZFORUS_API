package com.fundzforus.server.exception;

public class UserProgramBookingAlreadyExistException extends RuntimeException {
    public UserProgramBookingAlreadyExistException(String message) {
        super(message);
    }
}
