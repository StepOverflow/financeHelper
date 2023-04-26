package ru.shapovalov.service;

import org.springframework.stereotype.Service;
import ru.shapovalov.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.dao.TransactionDao;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@Service
public class TransactionService {
    private final TransactionDao transactionDao;
    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;

    public TransactionService() {
        transactionDao = new TransactionDao();
        transactionDtoConverter = new TransactionModelToTransactionDtoConverter();
    }

    public List<TransactionDto> getIncomeInPeriod(int categoryId, int userId, Timestamp startDate, Timestamp endDate){
        return transactionDao.getAllIncomeInPeriod(categoryId, userId, startDate, endDate).stream()
                .map(transactionDtoConverter::convert)
                .collect(toList());
    }
    public List<TransactionDto> getExpensesInPeriod(int categoryId, int userId, Timestamp startDate, Timestamp endDate){
        return transactionDao.getAllExpensesInPeriod(categoryId, userId, startDate, endDate).stream()
                .map(transactionDtoConverter::convert)
                .collect(toList());
    }
}
