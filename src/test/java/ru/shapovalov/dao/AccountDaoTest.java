package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.shapovalov.entity.Account;

import java.util.UUID;

import static org.junit.Assert.*;

public class AccountDaoTest {
    private AccountDao accountDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_account_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        accountDaoSubj = context.getBean(AccountDao.class);
    }

    @Test
    public void testInsert() {
        Account account = accountDaoSubj.insert("test_account", 1);

        assertEquals("test_account", account.getName());
        assertEquals(0, account.getBalance());
        assertTrue(account.getId() > 0);
        assertEquals(1, account.getUser().getId());
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