package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    @NotNull
    public Long id;
    @NotBlank
    public String name;
    public String description;
    public Boolean available;
    public User owner;
    public ItemRequest request;
}