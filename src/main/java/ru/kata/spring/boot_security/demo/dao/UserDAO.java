package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO implements UserDAOInterface {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public List<User> getThemAll(/*int count*/) {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public Optional<User> getThemById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = getThemById(id).get();
        em.remove(user);
    }

}
