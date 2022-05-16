package com.example.activiti.exception;

public class InvalidProcessNameException extends RuntimeException {
    public InvalidProcessNameException(String message) {
        super(message);
    }
}
