package ru.practicum.shareit.item.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@UtilityClass
public class ItemMapper {

    public ItemDto itemToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(item.getRequest() != null ? item.getRequest() : null)
                .build();
    }

    public Item dtoToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId() != null ? itemDto.getId() : 0)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(itemDto.getRequest() != null ? itemDto.getRequest() : null)
                .build();
    }

    public Item dtoToItemUpdate(ItemDto itemDto, Item item) {
        return Item.builder()
                .id(itemDto.getId() != null ? itemDto.getId() : item.getId())
                .name((itemDto.getName() != null && !itemDto.getName().isBlank()) ? itemDto.getName() : item.getName())
                .description((itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) ? itemDto.getDescription() : item.getDescription())
                .available(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable())
                .request(item.getRequest())
                .owner(item.getOwner())
                .build();
    }

}
