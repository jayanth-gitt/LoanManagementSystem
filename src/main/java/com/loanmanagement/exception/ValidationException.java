package com.loanmanagement.exception;

public class ValidationException extends IllegalArgumentException {

    public ValidationException(String message) {
        super(message);
    }
}