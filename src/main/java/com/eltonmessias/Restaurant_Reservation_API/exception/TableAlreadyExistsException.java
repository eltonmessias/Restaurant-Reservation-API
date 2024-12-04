package com.eltonmessias.Restaurant_Reservation_API.exception;

public class TableAlreadyExistsException extends RuntimeException{
    public TableAlreadyExistsException(String message){super(message);}
}
