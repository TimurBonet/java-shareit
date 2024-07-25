package ru.practicum.shareit.user.dto.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@UtilityClass
@Slf4j
public class UserMapper {

    public static UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId() != null ? userDto.getId() : 0)
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    public static User dtoToUserUpdate(UserDto userDto, User user) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName() != null ? userDto.getName() : user.getName())
                .email(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail())
                .build();
    }
}
