package com.emilija.ordersservice.exception;
public class NotFound extends RuntimeException{
    public NotFound(String msg) {
        super(msg);
    }
}
