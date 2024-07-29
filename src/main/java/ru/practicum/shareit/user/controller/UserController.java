package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.validationgroups.Marker;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Get all users");
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public UserDto getUserById(@PathVariable("userId") long userId) {
        log.info("Get user by id: {}", userId);
        return userService.getUserById(userId);
    }

    @PostMapping
    @Validated({Marker.Create.class})
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Create user: {}", userDto);
        return userService.createUser(userDto);
    }

    @PatchMapping("{userId}")
    @Validated({Marker.Update.class})
    public UserDto updateUser(@PathVariable long userId, @Valid @RequestBody UserDto userDto) {
        log.info("Update user: {} with id {}", userDto, userId);
        userDto.setId(userId);
        return userService.updateUser(userDto);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Delete user: {}", userId);
        userService.deleteUser(userId);
    }
}
