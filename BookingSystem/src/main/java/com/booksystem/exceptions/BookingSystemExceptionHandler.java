package com.booksystem.exceptions;

import com.booksystem.exceptions.userexceptions.UserNotFoundException;
import com.booksystem.exceptions.userexceptions.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class BookingSystemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse>validationExceptionHandler(BookingValidationException exception){
        ErrorResponse errorResponse=new ErrorResponse(exception.getMessage(),HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse>ApiExceptionHandler(ApiException exception){
        ErrorResponse errorResponse=new ErrorResponse(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>ApiExceptionHandler(BookingNotFoundException exception){
        ErrorResponse errorResponse=new ErrorResponse(exception.getMessage(),HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponse>ApiExceptionHandler(ResourceAlreadyExistException exception){
        ErrorResponse errorResponse=new ErrorResponse(exception.getMessage(),HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

}
