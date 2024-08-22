package ru.kata.spring.boot_security.demo.converter;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserConverter(PasswordEncoder passwordEncoder, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()));
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }

        if (dto.getRoles() != null) {
            Set<Role> roles = dto.getRoles().stream().map(roleStr -> roleService.findByName("ROLE_" + roleStr)).collect(Collectors.toSet());
            user.setRoles(roles);
        }
        return user;
    }
}
