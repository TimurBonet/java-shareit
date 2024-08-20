package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.validationgroups.Marker;

import java.util.List;

@Data
@Builder
public class ItemDto {
    public long id;
    @NotBlank(groups = {Marker.Create.class}, message = "поле name не заполнено")
    public String name;
    @NotNull(groups = {Marker.Create.class}, message = "поле description не заполнено")
    public String description;
    @NotNull(groups = {Marker.Create.class}, message = "поле available не заполнено")
    public Boolean available;
    private long ownerId;
    private List<CommentDto> comments;
    private ResponseBookingDto lastBooking;
    private ResponseBookingDto nextBooking;
}