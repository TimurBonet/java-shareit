package ru.practicum.shareit.item.dto.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@UtilityClass
@Slf4j
public class ItemMapper {

    public static ItemDto itemToDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .request(item.getRequest() != null ? item.getRequest() : null)
                .build();
    }

    public static Item dtoToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId() != null ? itemDto.getId() : 0)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .request(itemDto.getRequest() != null ? itemDto.getRequest() : null)
                .build();
    }

    public static Item dtoToItemUpdate(ItemDto itemDto, Item item) {
        return Item.builder()
                .id(itemDto.getId() != null ? itemDto.getId() : item.getId())
                .name(itemDto.getName() != null ? itemDto.getName() : item.getName())
                .description(itemDto.getDescription() != null ? itemDto.getDescription() : item.getDescription())
                .available(itemDto.getAvailable() != null ? itemDto.getAvailable() : item.getAvailable())
                .request(item.getRequest())
                .owner(item.getOwner())
                .build();
    }

}
