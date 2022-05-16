package com.example.activiti.exception;

public class InvalidTaskAssigneeException extends RuntimeException {
    public InvalidTaskAssigneeException(String message) {
        super(message);
    }
}
