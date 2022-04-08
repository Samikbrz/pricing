package com.example.pricing.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiRequestException{

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
