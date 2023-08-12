package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    public User findByEmailAndHash(String email, String hash) {
        try {
            return em.createQuery("SELECT u FROM User AS u WHERE u.email = :email and u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", hash)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Transactional
    public User insert(String email, String hash) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(hash);
        em.persist(user);
        return user;
    }

    public User findByUserId(Long userId) {
        return em.createQuery("SELECT u FROM User AS u WHERE u.id = :id", User.class)
                .setParameter("id", userId)
                .getSingleResult();
    }
}