package ru.kata.spring.boot_security.demo.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Role createRoleIfNotFound(String roleStr) {
        return roleRepository.findByName(roleStr).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(roleStr);
            roleRepository.save(newRole);
            return newRole;
        });
    }

    @Transactional
    public User createUserIfNotFound(String userStr, List<Role> roles) {
        return userRepository.getUserByUsername(userStr).orElseGet(() -> {
            User newUser = new User(userStr);
            newUser.setPassword(passwordEncoder.encode(userStr));
            if (userStr.equals("admin")) {
                newUser.addRole(roles.get(0));
                newUser.addRole(roles.get(1));
            } else if (userStr.equals("user")) {
                newUser.addRole(roles.get(1));
            }
            return newUser;
        });
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        List<Role> roles = Stream.of("ROLE_ADMIN", "ROLE_USER").map(this::createRoleIfNotFound).toList();
        List<User> users = Stream.of("admin", "user").map(userStr -> createUserIfNotFound(userStr, roles)).toList();

        userRepository.saveAll(users);

        alreadySetup = true;
    }

}
