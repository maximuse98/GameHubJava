package com.gamehub.exception;

public class NotFoundException extends Exception {
    private final ExceptionType type;

    public NotFoundException(ExceptionType type) {
        this.type = type;
    }

    public ExceptionType getType(){
        return type;
    }
}
