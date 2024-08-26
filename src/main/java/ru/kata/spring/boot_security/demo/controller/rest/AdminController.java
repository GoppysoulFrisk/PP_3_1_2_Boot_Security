package ru.kata.spring.boot_security.demo.controller.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.converter.UserConverter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/users")
public class AdminController {
    private final UserService userService;
//    private final RoleService roleService;
//    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    public AdminController(UserService userService, /*RoleService roleService, PasswordEncoder passwordEncoder,*/ UserConverter userConverter) {
        this.userService = userService;
//        this.roleService = roleService;
//        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public Set<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(userConverter::convertToDTO).collect(Collectors.toSet());
    }

    @PostMapping
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        userService.save(user);
        return userConverter.convertToDTO(userService.findById(user.getId()));
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
            userService.delete(id);
        return "User by id " + id + " deleted";
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        User user = userConverter.convertToEntity(userDTO);
        userService.update(user);
        return userConverter.convertToDTO(user);
    }

}
