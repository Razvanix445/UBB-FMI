package com.example.lab7.exceptions;

public class MessageInexistentException extends RuntimeException {
    public MessageInexistentException(String message) {
        super(message);
    }
}
