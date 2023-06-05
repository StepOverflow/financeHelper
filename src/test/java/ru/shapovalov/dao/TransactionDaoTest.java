package ru.shapovalov.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.shapovalov.dao.DaoConfiguration.getDataSource;
import static ru.shapovalov.dao.DaoFactory.getTransactionDao;

public class TransactionDaoTest {
    private TransactionDao transactionDao;

    private DataSource dataSource;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_transaction_dao_test.xml");

        transactionDao = getTransactionDao();
        dataSource = getDataSource();
    }

    @After
    public void tearDown() {
        transactionDao = null;
        dataSource = null;
    }

    @Test
    public void moneyTransfer() {
        Integer fromAccount = 1;
        Integer toAccount = 2;
        int amount = 100;
        List<Integer> categoryIds = new ArrayList<>(Arrays.asList(1, 2));

        TransactionModel transactionModel = transactionDao.moneyTransfer(fromAccount, toAccount, amount, 1, categoryIds);

        assertNotNull(transactionModel);
        assertEquals(fromAccount, transactionModel.getSender());
        assertEquals(toAccount, transactionModel.getRecipient());
        assertEquals(amount, transactionModel.getSum());
    }
}