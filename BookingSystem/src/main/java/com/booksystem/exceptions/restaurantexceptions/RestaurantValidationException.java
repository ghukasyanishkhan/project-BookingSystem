package com.booksystem.exceptions.restaurantexceptions;

public class RestaurantValidationException extends RuntimeException{
    public RestaurantValidationException(String message){
        super(message);
    }
}
