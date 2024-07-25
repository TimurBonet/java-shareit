package ru.practicum.shareit.item.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("{itemId}")
    public ItemDto getItemById(@PathVariable long itemId, HttpServletRequest request) {
        log.info("Getting item by id {}", itemId);
        return itemService.getItemById(itemId, request.getIntHeader("X-Sharer-User-Id"));
    }

    @GetMapping
    public List<ItemDto> getItemsByOwnerId(HttpServletRequest request) {
        log.info("Getting items by owner id{}", request.getIntHeader("X-Sharer-User-Id"));
        return itemService.getItemsByOwnerId(request.getIntHeader("X-Sharer-User-Id"));
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsBySearch(@RequestParam String text) {
        log.info("Getting items by search {}", text);
        return itemService.getItemsBySearch(text);
    }

    @PostMapping
    public ItemDto createItem(@RequestBody ItemDto itemDto, HttpServletRequest request) {
        log.info("Creating item {} by user id {}", itemDto, request.getIntHeader("X-Sharer-User-Id"));
        return itemService.createItem(itemDto, request.getIntHeader("X-Sharer-User-Id"));
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable long itemId, @RequestBody ItemDto itemDto, HttpServletRequest request) {
        log.info("Updating item {}, itemId {}, userId {}", itemDto, itemId, request.getIntHeader("X-Sharer-User-Id"));
        itemDto.setId(itemId);
        return itemService.updateItem(itemDto, request.getIntHeader("X-Sharer-User-Id"));
    }
}
