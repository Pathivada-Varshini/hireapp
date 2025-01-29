package com.hireportal.demo.exceptions;

import lombok.Data;

public class InvalidEmailException extends Exception {
    private String message;
    public InvalidEmailException(String message){
        this.message = message;
    }
}
