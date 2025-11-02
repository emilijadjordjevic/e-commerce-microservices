package com.emilija.usersservice.exception;

public class NotFound extends RuntimeException{
    public NotFound(String msg) {
        super(msg);
    }
}
