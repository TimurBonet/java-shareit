package ru.practicum.shareit.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(NotFoundException e) {
        log.warn("Возникло исключение NotFoundException. {}", e.getMessage(), e);
        return new ErrorResponse("Данные не найдены", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        log.warn("Возникло исключение EmailAlreadyUsedException. {}", e.getMessage(), e);
        return new ErrorResponse("Почта уже используется", e.getMessage());
    }

    @ExceptionHandler({BadHeaderException.class, EmptyFieldsException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(Exception e) {
        log.warn("Возникло исключение BadRequestException. {}", e.getMessage(), e);
        return new ErrorResponse("Переданы некорректные данные", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnhandledException(Throwable e) {
        log.error("Возникло необработанное исключение. {}", e.getMessage(), e);
        return new ErrorResponse("Необработанное исключение", e.getMessage());
    }
}
