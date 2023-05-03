package ru.shapovalov.dao;

import liquibase.Liquibase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


public class AccountDaoTest {
    AccountDao accountSubj;
    UserDao userSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID().toString());
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        accountSubj = context.getBean(AccountDao.class);
        userSubj = context.getBean(UserDao.class);

    }

    @Test
    public void getAllByUserId_ok() {
        AccountModel accountModel1 = new AccountModel();
        accountModel1.setUserId(2);
        accountModel1.setId(1);
        accountModel1.setName("account1");

        AccountModel accountModel2 = new AccountModel();
        accountModel2.setUserId(2);
        accountModel2.setId(2);
        accountModel2.setName("account2");

        List<AccountModel> expectedList = Arrays.asList(accountModel1, accountModel2);

        userSubj.insert("user1", "hash");
        userSubj.insert("user2", "hash");

        accountSubj.insert("account1", 2);
        accountSubj.insert("account2", 2);

        List<AccountModel> actualList = accountSubj.getAllByUserId(2);

        assertArrayEquals(expectedList.toArray(), actualList.toArray());
        assertFalse(actualList.isEmpty());
    }

    @Test
    public void delete_ok() {
        userSubj.insert("user1", "hash");
        userSubj.insert("user2", "hash");

        AccountModel accountModel = accountSubj.insert("accName", 2);

        assertTrue(accountSubj.delete(accountModel.getId(), accountModel.getUserId()));
    }

    @Test
    public void delete_noHaveRoots() {
        userSubj.insert("user1", "hash");
        userSubj.insert("user2", "hash");
        userSubj.insert("user3", "hash");

        AccountModel accountModel = accountSubj.insert("accName", 2);

        assertFalse(accountSubj.delete(accountModel.getId(), 3));
    }
}