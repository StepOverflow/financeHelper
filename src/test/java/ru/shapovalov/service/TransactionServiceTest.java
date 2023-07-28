package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.dao.TransactionDao;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @Mock
    TransactionDao transactionDao;

    @Test
    public void moneyTransfer_ok() {
        Account sender = new Account();
        sender.setId(1);
        Account recipient = new Account();
        recipient.setId(2);
        int sum = 100;

        List<Integer> categoryIds = new ArrayList<>(Arrays.asList(1, 2));

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(1);
        expectedTransaction.setFromAccount(sender);
        expectedTransaction.setToAccount(recipient);
        expectedTransaction.setAmountPaid(sum);

        when(transactionDao.moneyTransfer(sender.getId(), recipient.getId(), sum, 1, categoryIds)).thenReturn(expectedTransaction);
        Transaction actualTransaction = transactionDao.moneyTransfer(sender.getId(), recipient.getId(), sum, 1, categoryIds);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void sendMoney_InvalidUserId_ReturnsNull() {
        Account sender = new Account();
        sender.setId(1);
        Account recipient = new Account();
        recipient.setId(2);
        int sum = 100;
        int wrongUser = 4;
        List<Integer> categoryIds = new ArrayList<>(Arrays.asList(1, 2));

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(1);
        expectedTransaction.setFromAccount(sender);
        expectedTransaction.setToAccount(recipient);
        expectedTransaction.setAmountPaid(sum);

        Transaction actualTransaction = transactionDao.moneyTransfer(sender.getId(), recipient.getId(), sum, wrongUser, categoryIds);

        assertNull(actualTransaction);
    }
}