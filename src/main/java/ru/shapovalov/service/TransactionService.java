package ru.shapovalov.service;

import ru.shapovalov.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.dao.TransactionDao;
import ru.shapovalov.dao.TransactionModel;

import java.util.List;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;

    public TransactionService(TransactionDao transactionDao, TransactionModelToTransactionDtoConverter transactionDtoConverter) {
        this.transactionDao = transactionDao;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public TransactionDto sendMoney(int sender, int recipient, int sum, int userId, List<Integer> categoryIds) {
        TransactionModel transactionModel;
        if (recipient == 0) {
            transactionModel = transactionDao.moneyTransfer(sender, null, sum, userId, categoryIds);
        } else {
            transactionModel = transactionDao.moneyTransfer(sender, recipient, sum, userId, categoryIds);
        }
        return transactionDtoConverter.convert(transactionModel);
    }
}