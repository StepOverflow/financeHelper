package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.Timestamp;
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

    public Category insert(String categoryName, int userId) {
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

    public boolean delete(int id, int userId) {
        Category category = entityManager.find(Category.class, id);
        if (category != null && category.getUser().getId() == userId) {
            entityManager.remove(category);
            return true;
        }

        return false;
    }

    public Category edit(int id, String newCategoryName, int userId) {
        try {
            Category category = entityManager.find(Category.class, id);
            if (category != null && category.getUser().getId() == userId) {
                category.setName(newCategoryName);
                return category;
            }
            return null;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public List<Category> getAllByUserId(int userId) {
        return entityManager.createNamedQuery("Category.getAllByUserId", Category.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    public Map<String, Long> getResultIncomeInPeriodByCategory(int userId, Timestamp startDate, Timestamp endDate) {
        Query query = entityManager.createQuery("SELECT c.name, SUM(t.amountPaid) " +
                        "FROM Transaction t " +
                        "JOIN t.toAccount a " +
                        "JOIN t.categories c " +
                        "WHERE t.createdDate BETWEEN :startDate AND :endDate " +
                        "AND a.user.id = :userId " +
                        "GROUP BY c.name")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userId", userId);
        List<Object[]> results = query.getResultList();
        Map<String, Long> resultMap = new HashMap<>();
        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long amount = (Long) result[1];
            resultMap.put(categoryName, amount);
        }
        return resultMap;
    }

    public Map<String, Long> getResultExpenseInPeriodByCategory(int userId, Timestamp startDate, Timestamp endDate) {
        Query query = entityManager.createQuery("SELECT c.name, SUM(t.amountPaid) " +
                        "FROM Transaction t " +
                        "JOIN t.fromAccount a " +
                        "JOIN t.categories c " +
                        "WHERE t.createdDate BETWEEN :startDate AND :endDate " +
                        "AND a.user.id = :userId " +
                        "GROUP BY c.name")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userId", userId);

        List<Object[]> results = query.getResultList();
        Map<String, Long> resultMap = new HashMap<>();
        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long amount = (Long) result[1];
            resultMap.put(categoryName, amount);
        }
        return resultMap;
    }
}