package ru.shapovalov.dao;

import static ru.shapovalov.dao.DaoConfiguration.getDataSource;

public class DaoFactory {
    private static AccountDao accountDao;
    private static CategoryDao categoryDao;
    private static UserDao userDao;
    private static TransactionDao transactionDao;

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao(getDataSource());
        }
        return accountDao;
    }

    public static CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao(getDataSource());
        }
        return categoryDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao(getDataSource());
        }
        return userDao;
    }

    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao(getDataSource());
        }
        return transactionDao;
    }
}