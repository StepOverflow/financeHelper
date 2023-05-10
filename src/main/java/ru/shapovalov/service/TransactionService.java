package ru.shapovalov.service;

import ru.shapovalov.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.dao.TransactionDao;
import ru.shapovalov.dao.TransactionModel;

import static ru.shapovalov.service.ServiceFactory.getAccountService;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;

    public TransactionService(TransactionDao transactionDao, TransactionModelToTransactionDtoConverter transactionDtoConverter) {
        this.transactionDao = transactionDao;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public TransactionDto sendMoney(int sender, int recipient, int sum, int userId) {
        int userIdOfAccount = getAccountService()
                .getById(sender)
                .getUserId();
        if (userIdOfAccount == userId) {
            TransactionModel transactionModel;
            if (recipient == 0) {
                transactionModel = transactionDao.moneyTransfer(sender, null, sum);
            } else transactionModel = transactionDao.moneyTransfer(sender, recipient, sum);
            return transactionDtoConverter.convert(transactionModel);
        }
        return null;
    }
}
