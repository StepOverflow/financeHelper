package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class UserDaoTest {
    UserDao userSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        userSubj = context.getBean(UserDao.class);
    }

    @Test
    public void findByEmailAndHash() {
        UserModel userModel = userSubj.insert("user1", "a722c63db8ec8625af6cf71cb8c2d939");
        UserModel user = userSubj.findByEmailAndHash("user1", "a722c63db8ec8625af6cf71cb8c2d939");

        assertEquals(1, user.getId());
        assertEquals("user1", user.getEmail());
        assertEquals("a722c63db8ec8625af6cf71cb8c2d939", user.getPassword());
    }

    @Test
    public void insert() {
        UserModel userModel = userSubj.insert("user", "hash");

        userSubj.findByEmailAndHash("user", "hash");

        assertEquals("user", userModel.getEmail());
    }
}