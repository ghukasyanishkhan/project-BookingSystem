package com.booksystem.exceptions.userexceptions;

import com.booksystem.exceptions.ResourceAlreadyExistException;

public class UserAlreadyExistException extends ResourceAlreadyExistException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
