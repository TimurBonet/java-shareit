package ru.practicum.shareit.user.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public class UserMapper {

    public UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId() != null ? userDto.getId() : 0)
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    public User dtoToUserUpdate(UserDto userDto, User user) {
        return User.builder()
                .id(userDto.getId())
                .name((userDto.getName() != null && !userDto.getName().isBlank()) ? userDto.getName() : user.getName())
                .email((userDto.getEmail() != null && !userDto.getEmail().isBlank()) ? userDto.getEmail() : user.getEmail())
                .build();
    }
}
