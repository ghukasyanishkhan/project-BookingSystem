package com.booksystem.exceptions.restaurantexceptions;

import com.booksystem.exceptions.BookingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends BookingNotFoundException {
    public RestaurantNotFoundException(String message){
        super(message);
    }
}
