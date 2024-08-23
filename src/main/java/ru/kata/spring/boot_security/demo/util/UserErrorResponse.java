package ru.kata.spring.boot_security.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
//@Component
public class UserErrorResponse {
    private String message;
    private Long timeStamp;

}
