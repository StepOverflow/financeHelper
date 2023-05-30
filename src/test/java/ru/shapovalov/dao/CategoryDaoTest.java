package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static ru.shapovalov.dao.DaoFactory.*;

public class CategoryDaoTest {
    private CategoryDao categoryDaoSubj;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_category_dao_test.xml");

        categoryDaoSubj = getCategoryDao();
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
        int categoryId = 5;
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
        int userId = 1;
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
        Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());

        Map<String, Integer> result = categoryDaoSubj.getResultExpenseInPeriodByCategory(userId, startDate, endDate);
        assertNotNull(result);
        assertTrue(result.containsKey("food"));
    }
}