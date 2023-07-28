package ru.shapovalov.service;

import ru.shapovalov.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public CategoryDto create(String categoryName, int userId) {
        Category category = categoryDao.insert(categoryName, userId);
        return categoryDtoConverter.convert(category);
    }

    public boolean delete(int id, int userId) {
        return categoryDao.delete(id, userId);
    }

    public CategoryDto edit(int id, String newCategoryName, int userId) {
        Category category = categoryDao.edit(id, newCategoryName, userId);
        return categoryDtoConverter.convert(category);
    }

    public Map<String, Long> getResultIncomeInPeriodByCategory(int userId, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(days);

        Timestamp startDate = Timestamp.valueOf(yesterday.atZone(ZoneId.systemDefault()).toLocalDateTime());
        Timestamp endDate = Timestamp.valueOf(now.atZone(ZoneId.systemDefault()).toLocalDateTime());

        return categoryDao.getResultIncomeInPeriodByCategory(userId, startDate, endDate);
    }

    public Map<String, Long> getResultExpenseInPeriodByCategory(int userId, int days) {
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