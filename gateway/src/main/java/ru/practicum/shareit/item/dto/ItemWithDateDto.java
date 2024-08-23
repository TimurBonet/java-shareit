package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ItemWithDateDto {
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
    private LocalDateTime start;
    private LocalDateTime end;
    private List<CommentDto> comments;
    private long requestId;
}
