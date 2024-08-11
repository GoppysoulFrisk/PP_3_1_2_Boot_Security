package ru.kata.spring.boot_security.demo.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserInfo {
    private String email;
    private String phone;
}
