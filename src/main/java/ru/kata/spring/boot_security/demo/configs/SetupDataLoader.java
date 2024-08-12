package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Services.RoleService;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final RoleService roleService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Role createRoleIfNotFound(String roleStr) {
        try {
            Role role = roleService.findByName(roleStr);
            return role;
        } catch (RoleNotFoundException e) {
            Role newRole = new Role();
            newRole.setName(roleStr);
            roleService.save(newRole);
            return newRole;
        }
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        //create user and admin roles if they don't exist and save them
        List<Role> roles = Stream.of("ROLE_ADMIN", "ROLE_USER").map(this::createRoleIfNotFound).toList();


        User adminUser = userService.findByUsername("admin").orElseGet(() -> {
            User newAdmin = new User("admin");
            newAdmin.addRole(roles.get(0));
            newAdmin.setPassword(passwordEncoder.encode("admin"));
            return newAdmin;
        });

        User regularUser = userService.findByUsername("user").orElseGet(() -> {
            User newUser =  new User("user");
            newUser.addRole(roles.get(1));
            newUser.setPassword(passwordEncoder.encode("user"));
            return newUser;
        });

        userService.saveAll(List.of(adminUser, regularUser));

        alreadySetup = true;
    }

}
