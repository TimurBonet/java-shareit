package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmptyFieldsException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.user.dto.mapper.UserMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new EmptyFieldsException("Email не заполнен");
        }

        log.debug("Try creating user: {}", userDto);
        return userToDto(userRepository.createUser(dtoToUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        checkId(userDto.getId());
        log.debug("Try updating user: {}", userDto);
        return userToDto(userRepository
                .updateUser(dtoToUserUpdate(userDto, userRepository.getUserById(userDto.getId()).get())));
    }

    @Override
    public UserDto getUserById(long id) {
        checkId(id);
        log.debug("Try get user by id: {}", id);
        return userToDto(userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.debug("Try get users");
        return userRepository.getAllUsers().stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(long id) {
        checkId(id);
        log.debug("Try to delete user by id: {}", id);
        userRepository.deleteUser(id);
    }

    private void checkId(long id) {
        if (userRepository.getUserById(id) == null)
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
    }
}
