package ru.shapovalov.service;

import org.springframework.stereotype.Service;
import ru.shapovalov.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.dao.CategoryModel;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public CategoryService(CategoryDao categoryDao, CategoryModelToCategoryDtoConverter categoryDtoConverter) {
        this.categoryDao = categoryDao;
        this.categoryDtoConverter = categoryDtoConverter;
    }

    public CategoryDto create(String categoryName, int userId) {
        CategoryModel categoryModel = categoryDao.insert(categoryName, userId);
        return categoryDtoConverter.convert(categoryModel);
    }

    public boolean delete(int id, int userId) {
        return categoryDao.delete(id, userId);
    }

    public CategoryDto edit(int id, String newCategoryName, int userId) {
        CategoryModel categoryModel = categoryDao.edit(id, newCategoryName, userId);
        return categoryDtoConverter.convert(categoryModel);
    }

    public Map<String, Integer> getResultIncomeInPeriodByCategory(int userId, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(days);

        Timestamp startDate = Timestamp.valueOf(yesterday.atZone(ZoneId.systemDefault()).toLocalDateTime());
        Timestamp endDate = Timestamp.valueOf(now.atZone(ZoneId.systemDefault()).toLocalDateTime());

        return categoryDao.getResultIncomeInPeriodByCategory(userId, startDate, endDate);
    }

    public Map<String, Integer> getResultExpenseInPeriodByCategory(int userId, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(days);

        Timestamp startDate = Timestamp.valueOf(yesterday.atZone(ZoneId.systemDefault()).toLocalDateTime());
        Timestamp endDate = Timestamp.valueOf(now.atZone(ZoneId.systemDefault()).toLocalDateTime());

        return categoryDao.getResultExpenseInPeriodByCategory(userId, startDate, endDate);
    }

    public List<CategoryDto> getAll(int userId) {
        return categoryDao.getAllByUserId(userId).stream()
                .map(categoryDtoConverter::convert)
                .collect(toList());
    }
}