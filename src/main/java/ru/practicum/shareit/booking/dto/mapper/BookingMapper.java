package ru.practicum.shareit.booking.dto.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;


@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking requestBookingToBookingDto(RequestBookingDto requestBookingDto);

    ResponseBookingDto bookingToResponseBookingDto(Booking booking);
}
