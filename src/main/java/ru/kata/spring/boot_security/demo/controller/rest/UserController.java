package ru.kata.spring.boot_security.demo.controller.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.converter.UserConverter;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.exception.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

    private final UserService userService;
//    private final UserErrorResponse userErrorResponse; //todo
    private final UserConverter userConverter;

    public UserController(UserService userService /*UserErrorResponse userErrorResponse*/, UserConverter userConverter) {
        this.userService = userService;
//        this.userErrorResponse = userErrorResponse;
        this.userConverter = userConverter;
    }

    @GetMapping("api/v1/user")
    public UserDTO userPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        System.out.println(user);
        UserDTO dto = userConverter.convertToDTO(user);
        System.out.println(dto);
        return dto;
    }
//
//    @PostMapping
//    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
//                                                 BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            StringBuilder errorMsg = new StringBuilder();
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            for (FieldError fieldError : fieldErrors) {
//                errorMsg.append(fieldError.getField())
//                        .append(" -- ")
//                        .append(fieldError.getDefaultMessage())
//                        .append("\n");
//            }
//            throw new UserNotCreatedException(errorMsg.toString());
//        }
//        userService.save(user);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

}
