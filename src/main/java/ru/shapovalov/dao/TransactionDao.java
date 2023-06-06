package ru.shapovalov.dao;

import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class TransactionDao {
    private final DataSource dataSource;

    public TransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionModel moneyTransfer(Integer fromAccount, Integer toAccount, int amountPaid, int userId, List<Integer> categoryIds) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            int accountBalance = getAccountBalance(connection, fromAccount);
            if (fromAccount != null && accountBalance < amountPaid) {
                throw new CustomException("Insufficient funds on the account");
            }
            TransactionModel transactionModel = createTransaction(connection, fromAccount, toAccount, amountPaid);

            boolean balanceUpdated = updateAccountBalances(connection, fromAccount, toAccount, amountPaid);
            if (!balanceUpdated) {
                connection.rollback();
                throw new CustomException("Balance cannot be updated");
            }
            boolean categoriesUpdated = setCategoriesOfTransactions(connection, transactionModel.getId(), categoryIds);
            if (!categoriesUpdated) {
                connection.rollback();
                throw new CustomException("Categories cannot be set");
            }
            connection.commit();

            return transactionModel;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
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
                transactionModel.setCreatedDate(timestamp);
                return transactionModel;
            } else {
                throw new SQLException("Creating transaction failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    private boolean updateAccountBalances(Connection connection, Integer fromAccountId, Integer toAccountId, int amountPaid) {
        boolean success = true;
        if (fromAccountId != null) {
            success = updateAccountBalance(connection, fromAccountId, -amountPaid);
        }
        if (toAccountId != null) {
            success = updateAccountBalance(connection, toAccountId, amountPaid);
        }
        return success;
    }

    private boolean updateAccountBalance(Connection connection, Integer accountId, int amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setInt(2, accountId);
            int affectedRows = ps.executeUpdate();
            return (affectedRows > 0);
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    private int getAccountBalance(Connection connection, Integer accountId) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("balance");
                } else {
                    throw new CustomException("Account not found");
                }
            }
        }
    }

    private boolean setCategoriesOfTransactions(Connection connection, int transactionId, List<Integer> categoryIds) {
        String sql = "INSERT INTO transactions_categories (transaction_id, category_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer categoryId : categoryIds) {
                if (categoryId != null) {
                    ps.setInt(1, transactionId);
                    ps.setInt(2, categoryId);
                    ps.addBatch();
                }
            }

            int[] rowsAffected = ps.executeBatch();
            return Arrays.stream(rowsAffected).allMatch(count -> count > 0);
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}