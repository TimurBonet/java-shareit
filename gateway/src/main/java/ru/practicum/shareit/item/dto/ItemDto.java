package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;

import java.util.List;

@Data
@Builder
public class ItemDto {
    private long id;
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;
    @NotNull
    private Boolean available;
    private long ownerId;
    private List<CommentDto> comments;
    private ResponseBookingDto lastBooking;
    private ResponseBookingDto nextBooking;
    private long requestId;
}
