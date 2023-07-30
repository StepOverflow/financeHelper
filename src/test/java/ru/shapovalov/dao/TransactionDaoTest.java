package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

public class TransactionDaoTest {
    private TransactionDao transactionDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_transaction_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        transactionDaoSubj = context.getBean(TransactionDao.class);
    }

    @Test
    public void moneyTransfer() {
        Integer fromAccount = 1;
        Integer toAccount = 2;
        int amount = 100;
        List categoryIds = Arrays.asList(1, 2);

        Transaction transaction = transactionDaoSubj.moneyTransfer(fromAccount, toAccount, amount, 1, categoryIds);

        assertNotNull(transaction);
        assertEquals(fromAccount, Optional.of(transaction.getFromAccount().getId()).get());
        assertEquals(toAccount, Optional.of(transaction.getToAccount().getId()).get());
        assertEquals(amount, transaction.getAmountPaid());
    }

    @Test
    public void moneyTransfer_InsufficientFunds_ThrowsCustomException() {
        assertThrows(CustomException.class, () -> transactionDaoSubj.moneyTransfer(1, 2, 1000000, 1, Arrays.asList(1, 2)));
    }

    @Test
    public void moneyTransfer_BalanceUpdateFailed_ThrowsCustomException() throws SQLException {
        DataSource mockDataSource = Mockito.mock(DataSource.class);
        Connection mockConnection = Mockito.mock(Connection.class);

        Mockito.when(mockDataSource.getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenThrow(new SQLException("Failed to establish connection"));

        TransactionDao transactionDao = new TransactionDao(mockDataSource);
        assertThrows(CustomException.class, () -> transactionDao.moneyTransfer(1, 2, 100, 1, Arrays.asList(1, 2)));
    }

    @Test
    public void moneyTransfer_CategoriesSetFailed_ThrowsCustomException() throws SQLException {
        DataSource mockDataSource = Mockito.mock(DataSource.class);
        Connection mockConnection = Mockito.mock(Connection.class);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);

        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(100);

        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Mockito.when(mockDataSource.getConnection()).thenReturn(mockConnection);

        TransactionDao transactionDao = new TransactionDao(mockDataSource);
        assertThrows(CustomException.class, () -> transactionDao.moneyTransfer(1, 2, 100, 1, Arrays.asList(1, 2)));
    }
}