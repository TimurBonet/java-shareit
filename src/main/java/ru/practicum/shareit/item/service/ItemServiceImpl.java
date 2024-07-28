package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadHeaderException;
import ru.practicum.shareit.exception.EmptyFieldsException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.item.dto.mapper.ItemMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public ItemDto createItem(ItemDto itemDto, long ownerId) {
        checkUserId(ownerId);
        if (itemDto.getAvailable() == null || itemDto.getDescription() == null || itemDto.getName() == null) {
            throw new EmptyFieldsException("Все поля не заполнены!");
        }
        if (itemDto.getName().isEmpty() || itemDto.getDescription().isEmpty()) {
            throw new EmptyFieldsException("Поле не заполнено");
        }
        log.debug("Creating item element : {}; for user {}", itemDto, ownerId);
        return itemToDto(itemRepository.createItem(dtoToItem(itemDto), ownerId));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, long ownerId) {
        checkUserId(ownerId);
        checkItemId(itemDto.getId());
        log.info("Updating item with id {} and ownerId {}", itemDto.getId(), ownerId);
        return itemToDto(itemRepository.updateItem(dtoToItemUpdate(itemDto, itemRepository
                .getItemById(itemDto.getId()).get()), ownerId));
    }

    @Override
    public ItemDto getItemById(long itemId, long ownerId) {
        checkUserId(ownerId);
        checkItemId(itemId);
        log.info("Retrieving item with id {} and ownerId {}", itemId, ownerId);
        return itemToDto(itemRepository.getItemById(itemId).get());
    }

    @Override
    public List<ItemDto> getItemsByOwnerId(long ownerId) {
        checkUserId(ownerId);
        log.info("Retrieving items by ownerId {}", ownerId);
        return itemRepository.getItemsByOwnerId(ownerId).stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getItemsBySearch(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        log.debug("Retrieving items by search {}", text);
        return itemRepository.getItemsBySearch(text.toLowerCase()).stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    private void checkUserId(long userId) {
        if (userId == -1) {
            throw new BadHeaderException("Нет пользователя с id " + userId + " в заголовке");
        }
        if (userService.getUserById(userId).equals(null)) {
            throw new NotFoundException("Пользователь с id: " + userId + " на найден");
        }

    }

    private void checkItemId(long itemId) {
        if (itemRepository.getItemById(itemId) == null) {
            throw new NotFoundException("Отсутствует предмет с id: " + itemId);
        }
    }
}
