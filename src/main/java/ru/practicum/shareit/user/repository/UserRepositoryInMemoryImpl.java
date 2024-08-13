/*
package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EmailAlreadyUsedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryInMemoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> uniqueEmails = new HashSet<>();
    private long userId = 0;

    @Override
    public User createUser(User user) {
        log.info("Creating new user: {}", user);
        user.setId(++userId);
        if (!uniqueEmails.add(user.getEmail())) {
            --userId;
            throw new EmailAlreadyUsedException("Почтовый адрес занят");
        }
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User user) {
        long id = user.getId();
        User currentUser = users.get(user.getId());
        if (!user.getEmail().equals(currentUser.getEmail())) {
            if (uniqueEmails.add(user.getEmail())) {
                uniqueEmails.remove(currentUser.getEmail());
            } else {
                throw new EmailAlreadyUsedException("Пользователь с такой почтой уже зарегистрирован!");
            }
        }
        log.debug("Updating user: {}, with id {}", user, user.getId());
        users.put(id, user);
        return user;
    }

    @Override
    public Optional<User> getUserById(long id) {
        log.info("Get user by id: {}", id);
        return Optional.ofNullable(Optional.ofNullable(users.get(id)).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Get all users");
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteUser(long id) {
        log.info("Deleting user by id: {}", id);
        uniqueEmails.remove(users.remove(id).getEmail());
    }
}
*/
