package ru.practicum.shareit.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(final String message) {
        super(message);
    }
}