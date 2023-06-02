package ru.shapovalov.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.shapovalov.dao.DaoConfiguration;
import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoConfiguration.getDataSource;
import static ru.shapovalov.dao.DaoFactory.*;

public class CategoryDaoTest {
    private CategoryDao categoryDaoSubj;
    private DataSource dataSource;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_category_dao_test.xml");

        categoryDaoSubj = getCategoryDao();
        dataSource = getDataSource();
    }

    @After
    public void tearDown() {
        categoryDaoSubj = null;
        dataSource = null;
    }

    @Test
    public void insert() {
        String categoryName = "New Category";
        int userId = 1;

        CategoryModel categoryModel = categoryDaoSubj.insert(categoryName, userId);
        assertNotNull(categoryModel);
        assertEquals(categoryName, categoryModel.getName());
        assertEquals(userId, categoryModel.getUserId());
    }

    @Test
    public void delete() {
        int categoryId = 3;
        int userId = 1;
        boolean isDeleted = categoryDaoSubj.delete(categoryId, userId);
        assertTrue(isDeleted);
    }

    @Test
    public void edit() {
        int categoryId = 4;
        int userId = 3;
        String newCategoryName = "New Name";

        CategoryModel categoryModel = categoryDaoSubj.edit(categoryId, newCategoryName, userId);
        assertNotNull(categoryModel);
        assertEquals(newCategoryName, categoryModel.getName());
        assertEquals(userId, categoryModel.getUserId());
        assertEquals(categoryId, categoryModel.getId());
    }

    @Test
    public void getResultIncomeInPeriodByCategory() {
        int userId = 1;
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
        Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());

        Map<String, Integer> result = categoryDaoSubj.getResultIncomeInPeriodByCategory(userId, startDate, endDate);
        assertNotNull(result);
        assertTrue(result.containsKey("salary"));
    }

    @Test
    public void getResultExpenseInPeriodByCategory() {
        int userId = 3;
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
        Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());

        Map<String, Integer> result = categoryDaoSubj.getResultExpenseInPeriodByCategory(userId, startDate, endDate);
        assertNotNull(result);
        assertTrue(result.containsKey("salary"));
    }

    @Test
    public void testSetCategoriesOfTransactions() {
        int transactionId = 1;
        List<Integer> categoryIds = Arrays.asList(1, 2);

        boolean result = categoryDaoSubj.setCategoriesOfTransactions(transactionId, categoryIds);

        assertTrue(result);
        assertTrue(categoriesAreAssociatedWithTransaction(transactionId, categoryIds));
    }

    private boolean categoriesAreAssociatedWithTransaction(int transactionId, List<Integer> categoryIds) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM transactions_categories WHERE transaction_id = ? AND category_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (Integer categoryId : categoryIds) {
                ps.setInt(1, transactionId);
                ps.setInt(2, categoryId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}