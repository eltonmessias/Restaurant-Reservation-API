package com.eltonmessias.Restaurant_Reservation_API.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
