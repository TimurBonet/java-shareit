package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDateDto;

import java.util.List;

public interface ItemService {

    ItemDto createItem(ItemDto itemDto, long ownerId);

    CommentDto createComment(CommentDto commentDto, long itemId, long userId);

    ItemDto updateItem(ItemDto itemDto, long itemId, long ownerId);

    ItemDto findById(long itemId);

    List<ItemWithDateDto> findByOwnerId(long ownerId);

    List<ItemDto> getItemsBySearch(String text);
}
