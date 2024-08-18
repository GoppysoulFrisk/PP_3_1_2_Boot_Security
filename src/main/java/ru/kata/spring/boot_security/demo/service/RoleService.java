package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.exception.RoleNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String name) throws RoleNotFoundException;

    Role save(Role role);

    List<Role> findAll();

    Role findById(Long id);
}
