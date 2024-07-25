package ru.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.booking.dto.BookingStatus;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class Booking {
    @NotNull
    private Long id;
    @NotNull(message = "Дата начала бронирования должна присутствовать")
    private LocalDateTime start;
    @NotNull(message = "Дата окончания бронирования должна присутствовать")
    private LocalDateTime end;
    @NotNull(message = "Арендованный предмет не может быть пустым")
    private Item item;
    @NotNull(message = "Id пользователя который бронирует не может быть пустым")
    private Long booker;
    private BookingStatus status;
}
