package com.eltonmessias.Restaurant_Reservation_API.exception;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message){ super(message); }
}
