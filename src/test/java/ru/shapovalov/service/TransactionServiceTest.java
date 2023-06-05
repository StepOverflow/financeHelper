package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.dao.TransactionDao;
import ru.shapovalov.dao.TransactionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @Mock
    TransactionDao transactionDao;

    @Test
    public void moneyTransfer_ok() {
        Integer sender = 1;
        Integer recipient = 2;
        int sum = 100;
        int userId = 3;
        List<Integer> categoryIds = new ArrayList<>(Arrays.asList(1, 2));

        TransactionModel expectedTransactionModel = new TransactionModel();
        expectedTransactionModel.setId(1);
        expectedTransactionModel.setSender(sender);
        expectedTransactionModel.setRecipient(recipient);
        expectedTransactionModel.setSum(sum);

        when(transactionDao.moneyTransfer(sender, recipient, sum, 1, categoryIds)).thenReturn(expectedTransactionModel);
        TransactionModel actualTransactionModel = transactionDao.moneyTransfer(sender, recipient, sum, 1, categoryIds);

        assertEquals(expectedTransactionModel, actualTransactionModel);
    }

    @Test
    public void sendMoney_InvalidUserId_ReturnsNull() {
        Integer sender = 1;
        Integer recipient = 2;
        int sum = 100;
        int userId = 3;
        int wrongUser = 4;
        List<Integer> categoryIds = new ArrayList<>(Arrays.asList(1, 2));

        TransactionModel expectedTransactionModel = new TransactionModel();
        expectedTransactionModel.setId(1);
        expectedTransactionModel.setSender(sender);
        expectedTransactionModel.setRecipient(recipient);
        expectedTransactionModel.setSum(sum);

        TransactionModel actualTransactionModel = transactionDao.moneyTransfer(sender, recipient, sum, wrongUser, categoryIds);

        assertNull(actualTransactionModel);
    }
}