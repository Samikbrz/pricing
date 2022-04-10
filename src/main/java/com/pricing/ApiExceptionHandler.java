package com.pricing;

import com.pricing.dto.ErrorResponse;
import com.pricing.exception.AlreadyExistException;
import com.pricing.exception.NotFoundException;
import com.pricing.exception.NotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleApiException(NotFoundException ex) {
        return new ErrorResponse("404", ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyExistException.class)
    public ErrorResponse handleIOException(AlreadyExistException ex) {
        return new ErrorResponse("400", ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotValidException.class)
    public ErrorResponse handleIOException(NotValidException ex) {
        return new ErrorResponse("400", ex.getMessage());
    }

}
