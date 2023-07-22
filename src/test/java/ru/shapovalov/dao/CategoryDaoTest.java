package ru.shapovalov.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.shapovalov.entity.Category;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.testng.AssertJUnit.*;

public class CategoryDaoTest {
    private CategoryDao categoryDaoSubj;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem" + UUID.randomUUID() + ";DB_CLOSE_DELAY=0");
        System.setProperty("jdbcUser", "sa");
        System.setProperty("jdbcPassword", "");
        System.setProperty("liquibaseFile", "liquibase_category_dao_test.xml");

        ApplicationContext context = new AnnotationConfigApplicationContext("ru.shapovalov");
        categoryDaoSubj = context.getBean(CategoryDao.class);
    }

    @Test
    public void insert() {
        String categoryName = "New Category";
        int userId = 1;

        Category category = categoryDaoSubj.insert(categoryName, userId);
        assertNotNull(category);
        assertEquals(categoryName, category.getName());
        assertEquals(userId, category.getUser().getId());
    }

    @Test
    public void delete() {
        assertTrue(categoryDaoSubj.delete(3, 1));
    }

    @Test
    public void edit() {
        int categoryId = 4;
        int userId = 3;
        String newCategoryName = "New Name";

        Category category = categoryDaoSubj.edit(categoryId, newCategoryName, userId);
        assertNotNull(category);
        assertEquals(newCategoryName, category.getName());
        assertEquals(userId, category.getUser().getId());
        assertEquals(categoryId, category.getId());
    }

    @Test
    public void getResultIncomeInPeriodByCategory() {
        int userId = 1;
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now().minusDays(300));
        Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());

        Map<String, Long> result = categoryDaoSubj.getResultIncomeInPeriodByCategory(userId, startDate, endDate);
        assertNotNull(result);
        assertTrue(result.containsKey("salary"));
    }

    @Test
    public void getResultExpenseInPeriodByCategory() {
        int userId = 3;
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now().minusDays(300));
        Timestamp endDate = Timestamp.valueOf(LocalDateTime.now());

        Map<String, Long> result = categoryDaoSubj.getResultExpenseInPeriodByCategory(userId, startDate, endDate);
        assertNotNull(result);
        assertTrue(result.containsKey("salary"));
    }
}