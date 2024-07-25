package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(NotFoundException e) {
        log.error("Возникло исключение NotFoundException. {}", e.getMessage());
        return new ErrorResponse("Данные не найдены", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        log.error("Возникло исключение EmailAlreadyUsedException. {}", e.getMessage());
        return new ErrorResponse("Почта уже используется", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmptyFieldsException(EmptyFieldsException e) {
        log.error("Возникло исключение EmptyFieldsException. {}", e.getMessage());
        return new ErrorResponse("Найдены пустые поля", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadHeaderException(BadHeaderException e) {
        log.error("Возникло исключение BadHeaderException. {}", e.getMessage());
        return new ErrorResponse("Некорректный заголовок", e.getMessage());
    }


}
