package ru.practicum.shareit.exception;

public class BadHeaderException extends RuntimeException {
    public BadHeaderException(final String message) {
        super(message);
    }
}