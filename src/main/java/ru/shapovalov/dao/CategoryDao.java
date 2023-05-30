package ru.shapovalov.dao;

import ru.shapovalov.exception.CustomException;
import ru.shapovalov.service.TransactionDto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CategoryDao {
    private final DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CategoryModel insert(String categoryName, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into categories (name, user_id) values (?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, categoryName);
            ps.setInt(2, userId);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(generatedKeys.getInt(1));
                categoryModel.setName(categoryName);
                categoryModel.setUserId(userId);

                return categoryModel;
            } else {
                throw new CustomException("Can`t generate !");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public boolean delete(int id, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM categories WHERE id = ? and user_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public CategoryModel edit(int id, String newCategoryName, int userId) {
        CategoryModel categoryModel = new CategoryModel();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("update categories set name = ? where id = ? and user_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCategoryName);
            ps.setInt(2, id);
            ps.setInt(3, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                categoryModel.setName(newCategoryName);
                categoryModel.setUserId(userId);
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    categoryModel.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryModel;
    }

    public Map<String, Integer> getResultIncomeInPeriodByCategory(int userId, Timestamp startDate, Timestamp endDate) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT c.name, SUM(t.amount_paid) " +
                            "FROM transactions t " +
                            "JOIN accounts a ON a.id = t.to_account_id " +
                            "JOIN transactions_categories tc ON tc.transaction_id = t.id " +
                            "JOIN categories c ON c.id = tc.category_id " +
                            "WHERE t.created_date BETWEEN ? AND ? " +
                            "AND a.user_id = ? " +
                            "GROUP BY c.name"
            );
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();
            Map<String, Integer> result = new HashMap<>();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                int amount = rs.getInt(2);
                result.put(categoryName, amount);
            }
            return result;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public Map<String, Integer> getResultExpenseInPeriodByCategory(int userId, Timestamp startDate, Timestamp endDate) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT c.name, SUM(t.amount_paid) " +
                            "FROM transactions t " +
                            "JOIN accounts a ON a.id = t.from_account_id " +
                            "JOIN transactions_categories tc ON tc.transaction_id = t.id " +
                            "JOIN categories c ON c.id = tc.category_id " +
                            "WHERE t.created_date BETWEEN ? AND ? " +
                            "AND a.user_id = ? " +
                            "GROUP BY c.name"
            );
            ps.setTimestamp(1, startDate);
            ps.setTimestamp(2, endDate);
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();
            Map<String, Integer> result = new HashMap<>();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                int amount = rs.getInt(2);
                result.put(categoryName, amount);
            }
            return result;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}