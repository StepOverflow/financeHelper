package ru.shapovalov.service;

import ru.shapovalov.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.dao.TransactionDao;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;

    public TransactionDto sendMoney(int sender, int recipient, int sum, int userId, List<Integer> categoryIds) {
        Transaction transaction;
        if (recipient == 0) {
            transaction = transactionDao.moneyTransfer(sender, null, sum, userId, categoryIds);
        } else {
            transaction = transactionDao.moneyTransfer(sender, recipient, sum, userId, categoryIds);
        }
        return transactionDtoConverter.convert(transaction);
    }
}