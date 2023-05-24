package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoFactory.*;

public class TransactionDaoTest {
    private TransactionDao transactionDao;
    private AccountDao accountDao;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_transaction_dao_test.xml");

        transactionDao = getTransactionDao();
        accountDao = getAccountDao();
    }

    @Test
    public void moneyTransfer() {
        int account1StartBalance = accountDao.getBalance(1);
        int account2StartBalance = accountDao.getBalance(2);
        int amount = 100;

        TransactionModel transactionModel = transactionDao.moneyTransfer(1, 2, amount);

        assertNotNull(transactionModel);
        assertEquals(account1StartBalance - amount, accountDao.getBalance(1));
        assertEquals(account2StartBalance + amount, accountDao.getBalance(2));
    }
}