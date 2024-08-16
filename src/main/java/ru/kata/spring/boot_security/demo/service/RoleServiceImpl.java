package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.exception.RoleNotFoundException;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(noRollbackFor = RoleNotFoundException.class)
    public Role findByName(String name) throws RoleNotFoundException {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException("Role "+name+" not found"));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(noRollbackFor = RoleNotFoundException.class)
    public Role findById(Long id) throws RoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role with id "+ id +" not found"));
    }
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
