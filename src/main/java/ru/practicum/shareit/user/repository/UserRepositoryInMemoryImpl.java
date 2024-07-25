package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.EmailAlreadyUsedException;
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
        if (!user.getEmail().equals(users.get(user.getId()).getEmail())) {
            if (uniqueEmails.add(user.getEmail())) {
                uniqueEmails.remove(users.get(user.getId()).getEmail());
            } else {
                throw new EmailAlreadyUsedException("Пользователь с такой почтой уже зарегистрирован.");
            }
        }
        log.debug("Updating user: {}, with id {}", user, user.getId());
        users.put(id, user);
        return users.get(user.getId());
    }

    @Override
    public User getUserById(long id) {
        log.info("Get user by id: {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Get all users");
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteUser(long id) {
        log.info("Deleting user by id: {}", id);
        uniqueEmails.remove(users.get(id).getEmail());
        users.remove(id);
    }
}
