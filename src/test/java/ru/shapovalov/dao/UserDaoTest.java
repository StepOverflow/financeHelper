package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.testng.Assert.assertThrows;

public class UserDaoTest {
    private UserDao userDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        userDaoSubj = context.getBean(UserDao.class);
    }

    @Test
    public void testFindByEmailAndHash() {
        User userModel = userDaoSubj.findByEmailAndHash("user1@example.com", "password1");
        assertNotNull(userModel);
        assertEquals("user1@example.com", userModel.getEmail());
        assertEquals("password1", userModel.getPassword());
    }

    @Test
    public void testInsert() {
        User userModel = userDaoSubj.insert("test3@example.com", "password3");
        assertNotNull(userModel);
        assertEquals("test3@example.com", userModel.getEmail());
        assertEquals("password3", userModel.getPassword());
    }

    @Test
    public void testInsertWithGeneratedKeys() {
        User userModel = userDaoSubj.insert("test4@example.com", "password4");
        assertNotNull(userModel);
        assertNotEquals(0, userModel.getId());
        assertEquals("test4@example.com", userModel.getEmail());
        assertEquals("password4", userModel.getPassword());
    }

    @Test
    public void testInsertDuplicateEmail() {
        assertThrows(CustomException.class, () -> userDaoSubj.insert("user1@example.com", "password3"));
    }
}