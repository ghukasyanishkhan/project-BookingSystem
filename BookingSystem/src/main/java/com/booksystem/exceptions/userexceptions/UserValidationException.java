package com.booksystem.exceptions.userexceptions;

import com.booksystem.exceptions.BookingValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserValidationException extends BookingValidationException {
  public UserValidationException(String message){
      super(message);
  }
}
