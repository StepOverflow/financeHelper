//package ru.shapovalov.service;
//
//import ru.shapovalov.dao.TransactionDao;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//import static java.util.stream.Collectors.toList;
//
//public class TransactionService {
//    private final TransactionDao transactionDao;
//    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;
//
//    public TransactionService() {
//        transactionDao = new TransactionDao();
//        transactionDtoConverter = new TransactionModelToTransactionDtoConverter();
//    }
//
//    public int getResultIncomeInPeriod(int userId, Timestamp startDate, Timestamp endDate) {
//        return transactionDao.getResultIncomeInPeriod(userId, startDate, endDate);
//    }
//
//    public int getResultExpensesInPeriod(int userId, Timestamp startDate, Timestamp endDate) {
//        return transactionDao.getResultExpensesInPeriod(userId, startDate, endDate);
//    }
//}
