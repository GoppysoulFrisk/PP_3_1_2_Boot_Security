package ru.kata.spring.boot_security.demo.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;

@RestController
@RequestMapping("/user")
public class UserController {

//    @GetMapping(path = "{userId}")
//    public User getUser(@PathVariable("userId") Long userId) {
//        return USERS.stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
//    }

    //@PreAuthorize("")
    //hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public String getAllUsers() {
        return "user";
    }

//    @PostMapping
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public void registerNewUser(@RequestBody User user) {
//        System.out.println(user);
//    }
//
//    @DeleteMapping(path = "/{userId}")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public void deleteUserById(@PathVariable("userId") Long userId) {
//        System.out.println(userId);
////        USERS.stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
//    }
//    @PutMapping(path = "/{userId}")
//    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
//    public void updateUserById(@PathVariable("userId") Long userId, @RequestBody User user) {
//        System.out.println(user + " " + userId);
//    }
}
