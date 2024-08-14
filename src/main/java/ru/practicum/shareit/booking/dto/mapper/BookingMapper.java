package ru.practicum.shareit.booking.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.RequestBookingDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;


@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking requestBookingDtoToBooking(RequestBookingDto requestBookingDto);

    @Mapping(target = "booker", source = "booker")
    ResponseBookingDto bookingToResponseBookingDto(Booking booking);
}
