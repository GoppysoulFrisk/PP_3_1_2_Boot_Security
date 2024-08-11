package ru.kata.spring.boot_security.demo.Services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    User findById(Long id);

    void save(User user);

    void saveAll(List<User> users);

    void update(User updatedUser);

    void delete(Long id);

    Optional<User> findByUsername(String username);
}
