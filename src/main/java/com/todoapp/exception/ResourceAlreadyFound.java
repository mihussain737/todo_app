package com.todoapp.exception;

public class ResourceAlreadyFound extends RuntimeException {
    private String message;
    public ResourceAlreadyFound(String message){
        super(message);
    }
}
