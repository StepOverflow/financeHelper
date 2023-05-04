package ru.shapovalov.dao;

import org.junit.*;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoFactory.getAccountDao;
import static ru.shapovalov.dao.DaoFactory.getUserDao;

public class AccountDaoTest {
    private AccountDao accountDaoSubj;
    private UserDao userDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");

        userDaoSubj = getUserDao();
        accountDaoSubj = getAccountDao();
    }

    @Test
    public void testGetAllByUserId() {
        userDaoSubj.insert("user1", "hash");
        accountDaoSubj.insert("account1", 1);
        accountDaoSubj.insert("account2", 1);

        List<AccountModel> actualList = accountDaoSubj.getAllByUserId(1);

        assertEquals(2, actualList.size());
    }

    @Test
    public void testDelete() {
        userDaoSubj.insert("user13", "hash");
        AccountModel accountModel = accountDaoSubj.insert("accName", 1);

        assertTrue(accountDaoSubj.delete(accountModel.getId(), accountModel.getUserId()));
    }
}
