package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.shapovalov.JpaConfiguration;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class UserDao {
    private final DataSource dataSource;

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfiguration.class);
    EntityManager em = context.getBean(EntityManager.class);

    public User findByEmailAndHash(String email, String hash) {
        User user = null;
        try {
            user = em.createNamedQuery("Users.getByEmailAndPassword", User.class)
                    .setParameter("email", email)
                    .setParameter("password", hash)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            return user;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public User insert(String email, String hash) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        User user = new User();
        user.setEmail(email);
        user.setPassword(hash);
        em.persist(user);
        transaction.commit();
        return user;
    }

    public User findByUserId(Integer userId) {
        return em.createNamedQuery("Users.getById", User.class)
                .setParameter("id", userId)
                .getSingleResult();
    }
}