package ru.zagirnur.restguzel.service;

import org.springframework.stereotype.Service;
import ru.zagirnur.restguzel.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public User createUserIfNotExists(User user) {
        if (users.containsKey(user.getLogin())) {
            throw new IllegalArgumentException("User with login " + user.getLogin() + " already exists");
        }
        users.put(user.getLogin(), user);
        return user;
    }

    public Iterable<User> getAllUsers() {
        return users.values();
    }

    public User getUserByLoginOrThrow(String login) {
        User user = users.get(login);
        if (user == null) {
            throw new IllegalArgumentException("User with login " + login + " does not exist");
        }
        return user;
    }

    public User updateUserIfExists(User user) {
        if (!users.containsKey(user.getLogin())) {
            throw new IllegalArgumentException("User with login " + user.getLogin() + " does not exist");
        }
        users.put(user.getLogin(), user);
        return user;
    }
}
