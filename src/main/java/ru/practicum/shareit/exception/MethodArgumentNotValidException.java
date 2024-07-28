package ru.practicum.shareit.exception;

public class MethodArgumentNotValidException extends RuntimeException {
    public MethodArgumentNotValidException(final String message) {
        super(message);
    }
}