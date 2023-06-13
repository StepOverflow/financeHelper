package ru.shapovalov.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.UUID;

import static org.junit.Assert.*;

public class AccountDaoTest {
    private AccountDao accountDaoSubj;

    private DataSource dataSource;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_account_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        accountDaoSubj = context.getBean(AccountDao.class);
    }

    @After
    public void tearDown() {
        accountDaoSubj = null;
        dataSource = null;
    }

    @Test
    public void testInsert() {
        AccountModel accountModel = accountDaoSubj.insert("test_account", 1);

        assertEquals("test_account", accountModel.getName());
        assertEquals(0, accountModel.getBalance());
        assertTrue(accountModel.getId() > 0);
        assertEquals(1, accountModel.getUserId());
    }

    @Test
    public void testGetAllByUserId() {
        assertEquals(2, accountDaoSubj.getAllByUserId(1).size());
        assertEquals(1, accountDaoSubj.getAllByUserId(2).size());
    }

    @Test
    public void testDelete() {
        assertTrue(accountDaoSubj.delete(3, 2));
        assertTrue(accountDaoSubj.getAllByUserId(2).isEmpty());
    }
    @Test
    public void testDeleteWrongUser() {
        assertFalse(accountDaoSubj.delete(3, 1));
    }
}