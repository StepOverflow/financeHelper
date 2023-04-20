package ru.shapovalov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private final DataSource dataSource;

    public TransactionDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/postgres");
        config.setUsername("postgres");
        config.setPassword("Pattaya2023");

        dataSource = new HikariDataSource(config);
    }

    public List<TransactionModel> getAllIncomeInPeriod(int categoryId, int userId, Timestamp startDate, Timestamp endDate) {
        List<TransactionModel> transactionModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM transactions t " +
                            " JOIN transactions_categories tc ON t.id = tc.transaction_id " +
                            " JOIN accounts a ON a.id = t.to_account_id " +
                            " WHERE tc.category_id = ? AND t.created_date BETWEEN ? AND ? " +
                            " AND a.user_id = ? ");
            ps.setInt(1, categoryId);
            ps.setTimestamp(2, startDate);
            ps.setTimestamp(3, endDate);
            ps.setInt(4, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                TransactionModel tm = new TransactionModel();
                tm.setId(rs.getInt("id"));
                tm.setSender(rs.getInt("from_account_id"));
                tm.setRecipient(rs.getInt("to_account_id"));
                tm.setSum(rs.getInt("amount_paid"));
                tm.setTimestamp(rs.getTimestamp("created_date"));
                transactionModels.add(tm);

            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return transactionModels;
    }
    public List<TransactionModel> getAllExpensesInPeriod(int categoryId, int userId, Timestamp startDate, Timestamp endDate) {
        List<TransactionModel> transactionModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM transactions t " +
                            " JOIN transactions_categories tc ON t.id = tc.transaction_id " +
                            " JOIN accounts a ON a.id = t.from_account_id " +
                            " WHERE tc.category_id = ? AND t.created_date BETWEEN ? AND ? " +
                            " AND a.user_id = ? ");
            ps.setInt(1, categoryId);
            ps.setTimestamp(2, startDate);
            ps.setTimestamp(3, endDate);
            ps.setInt(4, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                TransactionModel tm = new TransactionModel();
                tm.setId(rs.getInt("id"));
                tm.setSender(rs.getInt("from_account_id"));
                tm.setRecipient(rs.getInt("to_account_id"));
                tm.setSum(rs.getInt("amount_paid"));
                tm.setTimestamp(rs.getTimestamp("created_date"));
                transactionModels.add(tm);

            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return transactionModels;
    }
}