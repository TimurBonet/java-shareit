package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EmptyFieldsException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new EmptyFieldsException("Email не заполнен");
        }

        log.debug("Try creating user: {}", userDto);
        return userMapper.userToDto(userRepository.save(userMapper.dtoToUser(userDto)));
    }

    @Override
    @Transactional
    public UserDto updateUserById(UserDto userDto) {
        checkId(userDto.getId());
        log.debug("Try updating user: {}", userDto);
        User userUpdate = userRepository.getReferenceById(userDto.getId());
        if(!userUpdate.getName().equals(userDto.getName()) && userDto.getName() != null) {
            userUpdate.setName(userDto.getName());
        }
        if(!userUpdate.getEmail().equals(userDto.getEmail()) && userDto.getEmail() != null) {
            userUpdate.setEmail(userDto.getEmail());
        }
        return userMapper.userToDto(userRepository.save(userUpdate));
    }

    @Override
    public UserDto findUserById(long id) {
        checkId(id);
        log.debug("Try get user by id: {}", id);
        return userMapper.userToDto(userRepository.getReferenceById(id));
    }

    @Override
    public List<UserDto> getAllUsers() {
        log.debug("Try get users");
        return  userRepository.findAll().stream().map(userMapper::userToDto).toList();
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        checkId(id);
        log.debug("Try to delete user by id: {}", id);
        userRepository.deleteById(id);
    }

    private void checkId(long id) {
        if (!userRepository.existsById(id))
            throw new NotFoundException("Пользователь с id " + id + " не найден.");
    }
}
