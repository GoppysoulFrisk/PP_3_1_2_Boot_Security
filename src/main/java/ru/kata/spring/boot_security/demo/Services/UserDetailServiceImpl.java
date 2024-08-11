package ru.kata.spring.boot_security.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: "+username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }
    @Override
    public void update(User updatedUser) {
        userRepository.save(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("couldn't find user with id " + id));
        userRepository.delete(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.getUserByUsername(username));
    }
}
