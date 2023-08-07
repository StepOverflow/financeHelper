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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @Mock
    TransactionDao transactionDao;

    @Test
    public void moneyTransfer_ok() {
        Account sender = new Account();
        sender.setId(1L);
        Account recipient = new Account();
        recipient.setId(2L);
        int sum = 100;

        List<Long> categoryIds = new ArrayList<>(Arrays.asList(1L, 2L));

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(1L);
        expectedTransaction.setFromAccount(sender);
        expectedTransaction.setToAccount(recipient);
        expectedTransaction.setAmountPaid(sum);

        when(transactionDao.moneyTransfer(sender.getId(), recipient.getId(), sum, 1L, categoryIds)).thenReturn(expectedTransaction);
        Transaction actualTransaction = transactionDao.moneyTransfer(sender.getId(), recipient.getId(), sum, 1L, categoryIds);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    public void sendMoney_InvalidUserId_ReturnsNull() {
        Account sender = new Account();
        sender.setId(1L);
        Account recipient = new Account();
        recipient.setId(2L);
        int sum = 100;
        long wrongUser = 4;
        List<Long> categoryIds = new ArrayList<>(Arrays.asList(1L, 2L));

        Transaction expectedTransaction = new Transaction();
        expectedTransaction.setId(1L);
        expectedTransaction.setFromAccount(sender);
        expectedTransaction.setToAccount(recipient);
        expectedTransaction.setAmountPaid(sum);

        Transaction actualTransaction = transactionDao.moneyTransfer(sender.getId(), recipient.getId(), sum, wrongUser, categoryIds);

        assertNull(actualTransaction);
    }
}