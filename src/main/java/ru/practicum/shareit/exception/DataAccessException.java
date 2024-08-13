package ru.practicum.shareit.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(final String message) {
        super(message);
    }
}