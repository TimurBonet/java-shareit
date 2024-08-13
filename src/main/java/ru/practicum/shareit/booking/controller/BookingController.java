package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.validationgroups.Marker;

import java.util.List;

import static ru.practicum.shareit.item.constants.Constant.HEADER;


@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public ResponseBookingDto findById(@PathVariable long bookingId,
                                       @RequestHeader(HEADER) long userId) {
        log.info("(GET) Поиск бронирования по Id: {}, от пользователя userId: {}", bookingId, userId);
        return bookingService.findById(bookingId, userId);
    }

    @GetMapping
    public List<ResponseBookingDto> findByBooker(@RequestHeader(HEADER) long bookerId,
                                                 @RequestParam(defaultValue = "ALL") State state) {
        log.info("(GET) Поучение всех бронирований текущего пользователя Id: {}, " +
                "со статусом state: {}", bookerId, state);
        return bookingService.findByBooker(bookerId, state);
    }

    @GetMapping("/owner")
    public List<ResponseBookingDto> findByOwner(@RequestHeader(HEADER) long ownerId,
                                                @RequestParam(defaultValue = "ALL") State state) {
        log.info("(GET) Поучение бронирований всех вещей текущего пользователя Id: {}, " +
                "со статусом state: {}", ownerId, state);
        return bookingService.findByOwner(ownerId, state);
    }

    @PostMapping
    public ResponseBookingDto create(@Validated({Marker.Create.class}) @RequestBody RequestBookingDto booking,
                                     @RequestHeader(HEADER) long userId) {
        log.info("(POST) Создание бронирования: {}, от пользователя Id = {}", booking, userId);
        return bookingService.create(booking, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseBookingDto update(@PathVariable long bookingId,
                                     @RequestHeader(HEADER) long ownerId,
                                     @RequestParam boolean approved) {
        log.info("(PATCH) Подтверждение(отклонение) запроса бронирования с id: {}, " +
                "от владельца ownerId = {}, статус подтверждения: {}", bookingId, ownerId, approved);
        return bookingService.update(bookingId, ownerId, approved);
    }
}
