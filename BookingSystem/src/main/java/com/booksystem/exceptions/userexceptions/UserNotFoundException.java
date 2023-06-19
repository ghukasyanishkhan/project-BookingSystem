package com.booksystem.exceptions.userexceptions;

import com.booksystem.exceptions.BookingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BookingNotFoundException {
    public UserNotFoundException(String message){
        super(message);
    }
}
