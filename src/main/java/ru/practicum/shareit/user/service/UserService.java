package ru.practicum.shareit.user.service;


import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto user);

    UserDto updateUserById(UserDto user);

    UserDto findUserById(long id);

    List<UserDto> getAllUsers();

    void deleteUserById(long id);

}
