package com.gamehub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundException extends Exception {
    private final ExceptionType type;

    public NotFoundException(ExceptionType type) {
        this.type = type;
    }

    public ExceptionType getType(){
        return type;
    }
}
