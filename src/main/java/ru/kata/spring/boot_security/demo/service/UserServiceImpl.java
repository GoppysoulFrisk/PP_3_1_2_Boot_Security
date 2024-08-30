package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(noRollbackFor = UserNotFoundException.class)
    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("couldn't find user with id " + id));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User updatedUser) {
        userRepository.save(updatedUser);
    }

    @Override
    public void delete(Long id) {

        if (id != null && id > 0) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Transactional(noRollbackFor = UserNotFoundException.class)
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException("couldn't find user with username " + username));
    }
}
