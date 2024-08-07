package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAOInterface {

    List<User> getThemAll(/*int count*/);

    Optional<User> getThemById(Long id);

    void save(User user);

    void update(User updatedUser);

    void delete(Long id);


}
