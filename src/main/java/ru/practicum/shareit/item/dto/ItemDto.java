package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validationgroups.Marker;

@Data
@Builder
public class ItemDto {
    public Long id;
    @NotBlank(groups = {Marker.Create.class})
    @NotNull(message = "поле name не заполнено")
    public String name;
    @NotNull(message = "поле description не заполнено")
    public String description;
    @NotNull(message = "поле available не заполнено")
    public Boolean available;
}