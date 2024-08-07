package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
        //todo почему ругается на имплементацию раобраться
//убрала имплементацию UserRepository, не ругается, потом посмотреть надо ли это вообще
//добавила abstract
public class UserService implements UserServiceInterface, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //UserDetailsService


    //может выползти lazyLoadExseption, manyToMany не грузится в режиме eager, стоит ленивая загрузка
//    но это только если нет транзакции
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);
        //todo обернуть в опшионал
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'", username));
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
                mapRolesToAutorities(user.get().getRoles()));
    }

    //для получения коллекции ролей и прав доступа
    //берет пачку ролей и делает грандэд авторитиз
    private Collection<? extends GrantedAuthority> mapRolesToAutorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //UserServiceInterface
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User updatedUser) {
        userRepository.save(updatedUser);
    }

//todo опшионал использован круто, просмотреть потом все на опшионал
    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("couldn't find user with id " + id));
        userRepository.delete(user);
    }
}
