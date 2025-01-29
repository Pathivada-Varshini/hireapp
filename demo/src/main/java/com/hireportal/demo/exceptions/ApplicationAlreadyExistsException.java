package com.hireportal.demo.exceptions;

public class ApplicationAlreadyExistsException extends RuntimeException {

    public ApplicationAlreadyExistsException(String message) {
        super(message);
    }
}
