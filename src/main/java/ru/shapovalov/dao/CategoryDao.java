package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@RequiredArgsConstructor
public class CategoryDao {
    private final DataSource dataSource;

    @PersistenceContext
    private final EntityManager entityManager;

    public Category insert(String categoryName, Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new CustomException("User not found");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setUser(user);
        entityManager.persist(category);

        return category;
    }

    public boolean delete(Long id, Long userId) {
        Category category = entityManager.find(Category.class, id);
        if (category != null && category.getUser().getId().equals(userId)) {
            entityManager.remove(category);
            return true;
        }

        return false;
    }

    public Category edit(Long id, String newCategoryName, Long userId) {
        try {
            Category category = entityManager.find(Category.class, id);
            if (category != null && category.getUser().getId().equals(userId)) {
                category.setName(newCategoryName);
                return category;
            }
            return null;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public List<Category> getAllByUserId(Long userId) {
        return entityManager.createQuery("SELECT c FROM Category AS c WHERE c.user.id = :user_id", Category.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    public Map<String, Long> getResultIncomeInPeriodByCategory(Long userId, Timestamp startDate, Timestamp endDate) {
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
            ps.setLong(3, userId);
            ResultSet rs = ps.executeQuery();
            Map<String, Long> result = new HashMap<>();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                long amount = rs.getLong(2);
                result.put(categoryName, amount);
            }
            return result;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public Map<String, Long> getResultExpenseInPeriodByCategory(Long userId, Timestamp startDate, Timestamp endDate) {
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
            ps.setLong(3, userId);
            ResultSet rs = ps.executeQuery();
            Map<String, Long> result = new HashMap<>();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                long amount = rs.getLong(2);
                result.put(categoryName, amount);
            }
            return result;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}