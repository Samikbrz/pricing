package com.example.pricing.exception;

import org.springframework.http.HttpStatus;

public class NotValidException extends ApiRequestException{

    public NotValidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public NotValidException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }
}
