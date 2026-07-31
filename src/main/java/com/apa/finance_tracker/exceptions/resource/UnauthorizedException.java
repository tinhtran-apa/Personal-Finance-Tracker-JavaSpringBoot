package com.apa.finance_tracker.exceptions.resource;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
