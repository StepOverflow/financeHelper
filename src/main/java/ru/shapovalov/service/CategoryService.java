package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.entity.Category;

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

    public CategoryDto create(String categoryName, Long userId) {
        Category category = categoryDao.insert(categoryName, userId);
        return categoryDtoConverter.convert(category);
    }

    public boolean delete(Long id, Long userId) {
        return categoryDao.delete(id, userId);
    }

    public CategoryDto edit(Long id, String newCategoryName, Long userId) {
        Category category = categoryDao.edit(id, newCategoryName, userId);
        return categoryDtoConverter.convert(category);
    }

    public Map<String, Long> getResultIncomeInPeriodByCategory(Long userId, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(days);

        Timestamp startDate = Timestamp.valueOf(yesterday.atZone(ZoneId.systemDefault()).toLocalDateTime());
        Timestamp endDate = Timestamp.valueOf(now.atZone(ZoneId.systemDefault()).toLocalDateTime());

        return categoryDao.getResultIncomeInPeriodByCategory(userId, startDate, endDate);
    }

    public Map<String, Long> getResultExpenseInPeriodByCategory(Long userId, int days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(days);

        Timestamp startDate = Timestamp.valueOf(yesterday.atZone(ZoneId.systemDefault()).toLocalDateTime());
        Timestamp endDate = Timestamp.valueOf(now.atZone(ZoneId.systemDefault()).toLocalDateTime());

        return categoryDao.getResultExpenseInPeriodByCategory(userId, startDate, endDate);
    }

    public List<CategoryDto> getAll(Long userId) {
        return categoryDao.getAllByUserId(userId).stream()
                .map(categoryDtoConverter::convert)
                .collect(toList());
    }
}