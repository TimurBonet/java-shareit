package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDateDto;
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
    public ItemDto findById(@PathVariable long itemId) {
        log.info("Getting item by id {}", itemId);
        return itemService.findById(itemId);
    }

    @GetMapping
    public List<ItemWithDateDto> getItemsByOwnerId(@RequestHeader(HEADER) long userId) {
        log.info("Getting items by owner id{}", userId);
        return itemService.findByOwnerId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsBySearch(@RequestParam(defaultValue = "") String text) {
        log.info("Getting items by search {}", text);
        return itemService.getItemsBySearch(text);
    }

    @PostMapping
    public ItemDto createItem(@Validated({Marker.Create.class}) @RequestBody ItemDto itemDto, @RequestHeader(HEADER) long userId) {
        log.info("Creating item {} by user id {}", itemDto, userId);
        return itemService.createItem(itemDto, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@Validated({Marker.Create.class}) @RequestBody CommentDto commentDto,
                                    @PathVariable long itemId,
                                    @RequestHeader(HEADER) long userId) {
        log.info("Creating comment {} by user id {} for item id {}", commentDto, userId, itemId);
        return itemService.createComment(commentDto, itemId, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable long itemId, @RequestBody ItemDto itemDto, @RequestHeader(HEADER) long userId) {
        log.info("Updating item {}, itemId {}, userId {}", itemDto, itemId, userId);
        return itemService.update(itemDto, itemId, userId);
    }
}
