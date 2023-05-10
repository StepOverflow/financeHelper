package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;
import ru.shapovalov.exception.CustomException;

import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoFactory.getUserDao;

public class UserDaoTest {
    UserDao userDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");

        userDaoSubj = getUserDao();
    }

    @Test
    public void testFindByEmailAndHash() {
        UserModel userModel = userDaoSubj.findByEmailAndHash("user1@example.com", "password1");
        assertNotNull(userModel);
        assertEquals("user1@example.com", userModel.getEmail());
        assertEquals("password1", userModel.getPassword());
    }

    @Test
    public void testInsert() {
        UserModel userModel = userDaoSubj.insert("test3@example.com", "password3");
        assertNotNull(userModel);
        assertEquals("test3@example.com", userModel.getEmail());
        assertEquals("password3", userModel.getPassword());
    }

    @Test
    public void testInsertWithGeneratedKeys() {
        UserModel userModel = userDaoSubj.insert("test4@example.com", "password4");
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
