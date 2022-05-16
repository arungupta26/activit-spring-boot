package com.example.activiti.exception;

public class InvalidDeploymentIdException extends RuntimeException {
    public InvalidDeploymentIdException(String message) {
        super(message);
    }
}
