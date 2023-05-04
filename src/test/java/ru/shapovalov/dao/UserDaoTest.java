package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;


import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoFactory.getUserDao;

public class UserDaoTest {
    UserDao subj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");

        subj = getUserDao();
    }

    @Test
    public void insert() {
        UserModel userModel = subj.insert("user", "hash");

        subj.findByEmailAndHash("user", "hash");

        assertEquals("user", userModel.getEmail());
    }

    @Test
    public void findByEmailAndHash() {
        UserModel userModel = subj.insert("user", "hash");

        UserModel user = subj.findByEmailAndHash("user", "hash");

        assertEquals(1, user.getId());
        assertEquals("user", user.getEmail());
        assertEquals("hash", user.getPassword());

    }
}
