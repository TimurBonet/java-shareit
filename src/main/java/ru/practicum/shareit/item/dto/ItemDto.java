package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
public class ItemDto {
    public Long id;
    public String name;
    public String description;
    public Boolean available;
    public User owner;
    public ItemRequest request;
}