package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Set;
import java.util.stream.Collectors;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping()
    public String getAllUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/usersPage";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roleIds", required = false) Set<Long> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Set<Role> roles = new HashSet<>();
//        for (Long roleId : roleIds) {
//            Role role = roleService.findById(roleId);
//            if (role != null) {
//                roles.add(role);
//            }
//        }
        user.setRoles(roleIds.stream().map(roleService::findById).collect(Collectors.toSet()));
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
            userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String getUpdatePage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "admin/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User newUserDetails, @RequestParam(value = "roleIds", required = false) Set<Long> roleIds) {
        User user = userService.findById(newUserDetails.getId());
        user.setRoles(roleIds.stream().map(roleService::findById).collect(Collectors.toSet()));
        user.setUsername(newUserDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newUserDetails.getPassword()));
        user.setEmail(newUserDetails.getEmail());
        user.setPhone(newUserDetails.getPhone());
        userService.update(user);
        return "redirect:/admin";
    }
}
