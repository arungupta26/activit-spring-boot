package com.example.activiti.exception;

public class InvalidProcessInstanceKeyException extends RuntimeException {
    public InvalidProcessInstanceKeyException(String message) {
        super(message);
    }
}
