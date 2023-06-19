package com.booksystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class BookingValidationException extends RuntimeException{
    protected BookingValidationException(String message){
        super(message);
    }
}
