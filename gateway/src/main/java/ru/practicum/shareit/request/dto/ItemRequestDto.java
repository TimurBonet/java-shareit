package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.shareit.booking.controller.BookingController.PATTERN;

@Data
@Builder
public class ItemRequestDto {
    private long id;
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;
    @JsonFormat(pattern = PATTERN)
    private LocalDateTime created;
    private List<ItemDto> items;
}
