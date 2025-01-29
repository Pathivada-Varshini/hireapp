package com.hireportal.demo.exceptions;

public class InvalidCredentialsException extends RuntimeException {  // Extending RuntimeException
    public InvalidCredentialsException(String message) {
        super(message);  // Pass the message to the parent constructor
    }
}
