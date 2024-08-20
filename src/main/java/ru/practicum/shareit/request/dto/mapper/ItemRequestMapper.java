package ru.practicum.shareit.request.dto.mapper;

import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {
    ItemRequestDto itemRequestToItemRequestDto(ItemRequest itemRequest);

    ItemRequest itemRequestDtoToItemRequest(ItemRequestDto itemRequestDto);
}
