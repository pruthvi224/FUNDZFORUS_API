package com.fundzforus.server.exception;

public class PartnerAlreadyExistException extends RuntimeException {
    public PartnerAlreadyExistException(String message) {
        super(message);
    }
}
