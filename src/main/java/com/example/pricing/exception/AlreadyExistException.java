package com.example.pricing.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends ApiRequestException {

    public AlreadyExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
