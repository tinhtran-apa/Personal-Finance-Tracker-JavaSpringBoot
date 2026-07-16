package com.apa.finance_tracker.exceptions.resource;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }
}
