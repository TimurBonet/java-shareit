package ru.practicum.shareit.exception;

public class EmptyFieldsException extends RuntimeException {
    public EmptyFieldsException(final String message) {
        super(message);
    }
}