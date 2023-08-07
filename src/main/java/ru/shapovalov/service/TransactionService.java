package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.dao.TransactionDao;
import ru.shapovalov.entity.Transaction;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;

    public TransactionDto sendMoney(Long sender, Long recipient, int sum, Long userId, List<Long> categoryIds) {
        Transaction transaction;
        if (recipient == 0) {
            transaction = transactionDao.moneyTransfer(sender, null, sum, userId, categoryIds);
        } else {
            transaction = transactionDao.moneyTransfer(sender, recipient, sum, userId, categoryIds);
        }
        return transactionDtoConverter.convert(transaction);
    }
}