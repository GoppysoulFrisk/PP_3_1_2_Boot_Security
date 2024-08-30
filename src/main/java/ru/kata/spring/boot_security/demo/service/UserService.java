package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    User findById(Long id);

    void save(User user, Set<Long> roleIds);

    void saveAll(List<User> users);

    void update(User updatedUser, Set<Long> roleIds);

    void delete(Long id);

    User findByUsername(String username) throws UserNotFoundException;
}
