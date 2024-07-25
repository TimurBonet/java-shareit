package ru.practicum.shareit.booking.dto;

import lombok.Getter;

@Getter
public enum BookingStatus {
    WAITING,
    APPROVED,
    REJECTED,
    CANCELED
}

