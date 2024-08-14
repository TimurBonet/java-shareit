package ru.practicum.shareit.booking.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.DataAccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingMapper bookingMapper;

    @Override
    public ResponseBookingDto create(RequestBookingDto creatingBooking, long userId) {
        log.info("Create booking with id {}", userId);

        if (creatingBooking.getStart().isAfter(creatingBooking.getEnd())) {
            throw new ValidationException("Начало бронирования не может быть позже конца бронирования");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Несуществующий пользователь"));
        Item item = itemRepository.findById(creatingBooking.getItemId())
                .orElseThrow(() -> new NotFoundException("Такого предмета не существует"));

        if (!item.getAvailable()) {
            throw new ValidationException("Предмет недоступен, забронируйте позже");
        }

        Booking booking = bookingMapper.requestBookingDtoToBooking(creatingBooking);
        booking.setBooker(user);
        booking.setItem(item);
        booking.setStatus(Status.WAITING);
        booking = bookingRepository.save(booking);
        log.info("Бронирование проведено {}", bookingMapper.bookingToResponseBookingDto(booking));
        return bookingMapper.bookingToResponseBookingDto(booking);
    }

    @Override
    public ResponseBookingDto update(long bookingId, long ownerId, boolean approved) {
        log.info("Подтверждение бронирования id = {}, approved = {}", bookingId, approved);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Нет такого бронирования"));

        if (booking.getItem().getOwner().getId() == ownerId) {
            booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        } else {
            throw new DataAccessException("Нет права доступа к этим настройкам");
        }

        log.info("Статус бронирования обновлен на approved = {}", approved);
        return bookingMapper.bookingToResponseBookingDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseBookingDto findById(long bookingId, long userId) {
        log.info("Получение бронирования id = {}, от польователя userId = {}", bookingId, userId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Нет такой брони"));

        if (booking.getItem().getOwner().getId() != userId && booking.getBooker().getId() != userId) {
            throw new DataAccessException("Нет права доступа к этой информации");
        }
        log.info("Бронирование успешно получено");
        return bookingMapper.bookingToResponseBookingDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseBookingDto> findByBooker(long bookerId, State state) {
        log.info("Получение всех броней пользователя  bookerId = {} со статусом  state = {}", bookerId, state);
        List<Booking> bookings;

        switch (state) {
            case ALL -> bookings = bookingRepository.findByBookerId(bookerId);
            case PAST -> bookings = bookingRepository.findAllBookingByBookerAndPast(bookerId, LocalDateTime.now());
            case CURRENT ->
                    bookings = bookingRepository.findAllBookingByBookerAndCurrent(bookerId, LocalDateTime.now());
            case FUTURE -> bookings = bookingRepository.findAllBookingByBookerAndFuture(bookerId, LocalDateTime.now());
            case REJECTED -> bookings = bookingRepository.findByBookerIdAndStatus(bookerId, Status.REJECTED.name());
            case WAITING -> bookings = bookingRepository.findByBookerIdAndStatus(bookerId, Status.WAITING.name());
            case null, default -> bookings = new ArrayList<>();
        }
        log.info("Список бронирований сформирован");
        return bookings.stream()
                .map(bookingMapper::bookingToResponseBookingDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseBookingDto> findByOwner(long ownerId, State state) {
        log.info("Получение списка бронированных вещей пользователя ownerId = {}, " +
                "со статусом state = {}", ownerId, state);
        List<Booking> bookings;

        switch (state) {
            case ALL:
                bookings = bookingRepository.findAllBookingByOwner(ownerId);
            case PAST:
                bookings = bookingRepository.findAllBookingByOwnerAndPast(ownerId, LocalDateTime.now());
            case CURRENT:
                bookings = bookingRepository.findAllBookingByOwnerAndCurrent(ownerId, LocalDateTime.now());
            case FUTURE:
                bookings = bookingRepository.findAllBookingByOwnerAndFuture(ownerId, LocalDateTime.now());
            case REJECTED:
                bookings = bookingRepository.findAllBookingByOwnerAndStatus(ownerId, Status.REJECTED.name());
            case WAITING:
                bookings = bookingRepository.findAllBookingByOwnerAndStatus(ownerId, Status.WAITING.name());
            case null, default:
                bookings = new ArrayList<>();
        }

        if (bookings.isEmpty()) {
            throw new NotFoundException("Список пуст");
        }

        log.info("Список бронирования вещей получен.");
        return bookings.stream()
                .map(bookingMapper::bookingToResponseBookingDto)
                .toList();
    }
}
