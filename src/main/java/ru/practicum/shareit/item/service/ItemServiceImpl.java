package ru.practicum.shareit.item.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.DataAccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.dto.mapper.CommentMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithDateDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ItemMapper itemMapper;
    private final BookingMapper bookingMapper;

    @Override
    public ItemDto createItem(ItemDto itemDto, long ownerId) {
        log.debug("Creating item element : {}; for user {}", itemDto, ownerId);
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + ownerId + " не найден"));
        Item item = itemMapper.dtoToItem(itemDto);
        item.setOwner(owner);
        return itemMapper.itemToDto(item);
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, long itemId, long userId) {
        log.debug("Creating comment for Item id: {}, for user {}", itemId, userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Такого предмета не существует"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такой пользователь отсутствует"));

        if (bookingRepository.findByUserId(userId, LocalDateTime.now()).isEmpty()) {
            throw new ValidationException("Данный пользователь не создавал эту вещь");
        }

        Comment comment = commentMapper.dtoToComment(commentDto);
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(comment);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, long id, long ownerId) {
        log.info("Updating item with id {} and ownerId {}", id, ownerId);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет с Id " + id + " отсутствует"));
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("Такой владелец отсутствует");
        }
        if (item.getOwner().getId() != ownerId) {
            throw new DataAccessException("У вас нет доступа к этой информации");
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return itemMapper.itemToDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto findById(long itemId) {
        log.info("Retrieving item with id {} and ownerId {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Такого предмета не существует"));
        List<CommentDto> comment = commentRepository.findByItemId(itemId).stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
        ItemDto itemDto = itemMapper.itemToDto(item);
        itemDto.setComments(comment);
        itemDto.setLastBooking(bookingMapper
                .bookingToResponseBookingDto(bookingRepository.findByItemIdPast(itemId, LocalDateTime.now(), Status.REJECTED)));
        itemDto.setNextBooking(bookingMapper
                .bookingToResponseBookingDto(bookingRepository.findByItemIdFuture(itemId, LocalDateTime.now(), Status.REJECTED)));
        return itemDto;
    }

    @Override
    public List<ItemWithDateDto> findByOwnerId(long ownerId) {
        log.info("Retrieving items by ownerId {}", ownerId);

        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("Такого владельца не существует");
        }
        List<Item> items = itemRepository.findByOwnerId(ownerId);
        List<ItemWithDateDto> itemWithDateDto = items.stream()
                .map(itemMapper::itemToItemWithDateDto)
                .toList();

        for (ItemWithDateDto item : itemWithDateDto) {
            Booking booking = bookingRepository.findByItemId(item.getId());
            List<CommentDto> comment = commentRepository.findByItemId(item.getId()).stream()
                    .map(commentMapper::commentToCommentDto)
                    .toList();
            if (booking != null) {
                item.setStart(booking.getStart());
                item.setEnd(booking.getEnd());
                item.setComments(comment);
            }
        }
        return itemWithDateDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getItemsBySearch(String text) {
        if (text.isBlank()) {
            log.info("Пустой запрос поиска");
            return new ArrayList<>();
        }
        log.debug("Retrieving items by search {}", text);
        List<Item> items = itemRepository.findBySearchText(text.toLowerCase());
        return items.stream()
                .map(itemMapper::itemToDto)
                .toList();
    }

}
