package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestBookingDto {
    private Long id;
    @NotNull(message = "Дата начала бронирования должна присутствовать")
    private LocalDateTime start;
    @Future
    @NotNull(message = "Дата окончания бронирования должна присутствовать")
    private LocalDateTime end;
    @NotNull(message = "Арендованный предмет не может быть пустым")
    private Long itemId;
    @NotNull(message = "Id пользователя который бронирует не может быть пустым")
    private Long bookerId;
    private Status status;
}