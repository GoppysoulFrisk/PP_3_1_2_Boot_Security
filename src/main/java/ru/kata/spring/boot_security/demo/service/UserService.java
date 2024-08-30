package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User findById(Long id);

    void save(User user);

    void update(User updatedUser);

    void delete(Long id);

    User findByUsername(String username) throws UserNotFoundException;
}
