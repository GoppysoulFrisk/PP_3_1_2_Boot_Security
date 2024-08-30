package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String getAllUsersPage(Model model) {
        model.addAttribute("currentPath", "/admin");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.findAll());
        return "admin/usersPage";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roleIds", required = false) Set<Long> roleIds) {
        userService.save(user, roleIds);
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
        userService.update(newUserDetails, roleIds);
        return "redirect:/admin";
    }
}
