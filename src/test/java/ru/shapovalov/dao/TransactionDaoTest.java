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
        Integer fromAccount = 1;
        Integer toAccount = 2;
        int amount = 100;

        TransactionModel transactionModel = transactionDao.moneyTransfer(fromAccount, toAccount, amount, 1);

        assertNotNull(transactionModel);
        assertEquals(fromAccount, transactionModel.getSender());
        assertEquals(toAccount, transactionModel.getRecipient());
        assertEquals(amount, transactionModel.getSum());
    }
}