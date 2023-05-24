package ru.shapovalov.dao;

import org.junit.*;

import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoFactory.getAccountDao;

public class AccountDaoTest {
    private AccountDao accountDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_account_dao_test.xml");

        accountDaoSubj = getAccountDao();
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
        AccountModel accountModel1 = accountDaoSubj.insert("test_account1", 1);
        AccountModel accountModel2 = accountDaoSubj.insert("test_account2", 1);
        AccountModel accountModel3 = accountDaoSubj.insert("test_account3", 2);

        assertEquals(2, accountDaoSubj.getAllByUserId(1).size());
        assertEquals(1, accountDaoSubj.getAllByUserId(2).size());
    }

    @Test
    public void testDelete() {
        AccountModel accountModel = accountDaoSubj.insert("test_account", 3);
        int accountId = accountModel.getId();

        assertTrue(accountDaoSubj.delete(accountId, 3));
        assertTrue(accountDaoSubj.getAllByUserId(3).isEmpty());
    }
}