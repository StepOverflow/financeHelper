package ru.shapovalov.dao;

public class DaoFactory {
    private static AccountDao accountDao;
    private static CategoryDao categoryDao;
    private static UserDao userDao;
    private static TransactionDao transactionDao;

    public static AccountDao getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDao();
        }
        return accountDao;
    }

    public static CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao();
        }
        return transactionDao;
    }
}
