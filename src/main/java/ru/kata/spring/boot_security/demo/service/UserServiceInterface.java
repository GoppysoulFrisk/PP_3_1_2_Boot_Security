package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserServiceInterface {
    List<User> getAllUsers();

    User findById(Long id);

    void save(User user);

    void update(User updatedUser);

    void delete(Long id);
}
