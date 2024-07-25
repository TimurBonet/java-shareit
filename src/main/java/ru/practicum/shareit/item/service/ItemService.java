package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto, long ownerId);

    ItemDto updateItem(ItemDto itemDto, long ownerId);

    ItemDto getItemById(long itemId, long ownerId);

    List<ItemDto> getItemsByOwnerId(long ownerId);

    List<ItemDto> getItemsBySearch(String text);
}
