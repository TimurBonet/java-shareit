package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validationgroups.Marker;

import static ru.practicum.shareit.item.constants.Constant.HEADER;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;


    @GetMapping("{itemId}")
    public ItemDto getItemById(@PathVariable long itemId, @RequestHeader(HEADER) String userId) {
        log.info("Getting item by id {}", itemId);
        return itemService.getItemById(itemId, Integer.parseInt(userId));
    }

    @GetMapping
    public List<ItemDto> getItemsByOwnerId(@RequestHeader(HEADER) String userId) {
        log.info("Getting items by owner id{}", userId);
        return itemService.getItemsByOwnerId(Integer.parseInt(userId));
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsBySearch(@RequestParam String text) {
        log.info("Getting items by search {}", text);
        return itemService.getItemsBySearch(text);
    }

    @PostMapping
    public ItemDto createItem(@Validated({Marker.Create.class}) @RequestBody ItemDto itemDto, @RequestHeader(HEADER) String userId) {
        log.info("Creating item {} by user id {}", itemDto, userId);
        return itemService.createItem(itemDto, Integer.parseInt(userId));
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable long itemId, @RequestBody ItemDto itemDto, @RequestHeader(HEADER) String userId) {
        log.info("Updating item {}, itemId {}, userId {}", itemDto, itemId, userId);
        itemDto.setId(itemId);
        return itemService.updateItem(itemDto, Integer.parseInt(userId));
    }
}
