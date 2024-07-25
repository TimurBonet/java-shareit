package ru.practicum.shareit.item.repository;


import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item createItem(Item item, long ownerId);

    Item updateItem(Item item, long ownerId);

    Item getItemById(long itemId);

    List<Item> getItemsByOwnerId(long ownerId);

    List<Item> getItemsBySearch(String text);

}
