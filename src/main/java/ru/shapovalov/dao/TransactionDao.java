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

    public int getResultIncomeInPeriod(int userId, Timestamp startDate, Timestamp endDate) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT SUM(amount_paid) FROM transactions t " +
                            " JOIN accounts a ON a.id = t.to_account_id " +
                            " WHERE t.created_date BETWEEN ? AND ? " +
                            " AND a.user_id = ? ");
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public int getResultExpensesInPeriod(int userId, Timestamp startDate, Timestamp endDate) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT SUM(amount_paid) FROM transactions t " +
                            " JOIN accounts a ON a.id = t.to_account_id " +
                            " WHERE t.created_date BETWEEN ? AND ? " +
                            " AND a.user_id = ? ");
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}