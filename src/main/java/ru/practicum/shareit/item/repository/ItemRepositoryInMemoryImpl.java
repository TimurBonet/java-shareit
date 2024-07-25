package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryInMemoryImpl implements ItemRepository {
    private final UserRepository userRepository;
    private final Map<Long, List<Item>> items = new HashMap<>();
    private long itemId = 0;

    @Override
    public Item createItem(Item item, long ownerId) {
        log.info("Creating new item");
        item.setId(++itemId);
        item.setOwner(userRepository.getUserById(ownerId));
        items.compute(ownerId, (id, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        log.debug("Try add new item {}", item);
        int index = getItemIndex(item.getId(), ownerId);
        return items.get(ownerId).get(index);
    }

    @Override
    public Item updateItem(Item item, long ownerId) {
        log.info("Updating existing item {}, ownerId {}", item, ownerId);
        if (ownerId != item.getOwner().getId()) {
            throw new NotFoundException("Id пользователя некорректно");
        }
        int index = getItemIndex(item.getId(), ownerId);
        items.get(ownerId).set(index, item);
        return items.get(ownerId).get(index);
    }

    @Override
    public Item getItemById(long itemId) {
        log.info("Getting item {}", itemId);
        Item item = null;
        for (Long ownerId : items.keySet()) {
            item = items.get(ownerId).stream()
                    .filter(i -> i.getId() == itemId)
                    .findFirst().orElse(null);
        }
        return item;
    }

    @Override
    public List<Item> getItemsByOwnerId(long ownerId) {
        log.info("Getting items by ownerId {}", ownerId);
        return items.get(ownerId);
    }

    @Override
    public List<Item> getItemsBySearch(String text) {
        List<Item> availableItems = new ArrayList<>();
        for (Long ownerId : items.keySet()) {
            availableItems.addAll(getItemsByOwnerId(ownerId).stream()
                    .filter(x -> x.getAvailable().equals(true))
                    .filter(x -> x.getDescription().toLowerCase().contains(text))
                    .collect(Collectors.toList()));
        }
        return availableItems;
    }

    private int getItemIndex(Long itemId, Long userId) {
        return IntStream.range(0, items.get(userId).size())
                .filter(i -> items.get(userId).get(i).getId().equals(itemId))
                .findFirst()
                .orElse(-1);
    }
}
