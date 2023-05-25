package ru.shapovalov.dao;


import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

import static ru.shapovalov.dao.DaoFactory.getAccountDao;

public class TransactionDao {
    private final DataSource dataSource;

    public TransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionModel moneyTransfer(Integer fromAccount, Integer toAccount, int amountPaid) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            if (fromAccount != null && getAccountDao().getBalance(fromAccount) < amountPaid) {
                throw new CustomException("Insufficient funds on the account");
            }
            TransactionModel transactionModel = createTransaction(connection, fromAccount, toAccount, amountPaid);
            updateAccountBalances(connection, fromAccount, toAccount, amountPaid);

            connection.commit();

            return transactionModel;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    return null;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new CustomException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private TransactionModel createTransaction(Connection connection, Integer fromAccount, Integer toAccount, int amountPaid) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO transactions (from_account_id, to_account_id, amount_paid, created_date) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            if (fromAccount == null) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, fromAccount);
            }
            if (toAccount == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, toAccount);
            }
            ps.setInt(3, amountPaid);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(4, timestamp);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int transactionId = rs.getInt(1);
                TransactionModel transactionModel = new TransactionModel();
                transactionModel.setId(transactionId);
                transactionModel.setSender(fromAccount);
                transactionModel.setRecipient(toAccount);
                transactionModel.setSum(amountPaid);
                transactionModel.setTimestamp(timestamp);
                return transactionModel;
            } else {
                throw new SQLException("Creating transaction failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    private void updateAccountBalances(Connection connection, Integer fromAccountId, Integer toAccountId, int amountPaid) {
        if (fromAccountId != null) {
            updateAccountBalance(connection, fromAccountId, -amountPaid);
        }
        if (toAccountId != null) {
            updateAccountBalance(connection, toAccountId, amountPaid);
        }
    }

    private void updateAccountBalance(Connection connection, Integer accountId, int amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}