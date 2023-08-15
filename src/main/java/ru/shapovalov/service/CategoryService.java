package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.CategoryRepository;
import ru.shapovalov.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public CategoryDto create(String categoryName, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Category category = new Category();
        category.setName(categoryName);
        category.setUser(user.get());

        category = categoryRepository.save(category);
        return categoryDtoConverter.convert(category);
    }

    public boolean delete(Long id, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(id, userId);
        if (category != null) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    public CategoryDto edit(Long id, String newCategoryName, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(id, userId);
        if (category != null) {
            category.setName(newCategoryName);
            category = categoryRepository.save(category);
            return categoryDtoConverter.convert(category);
        }
        return null;
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
        return categoryRepository.findByUserId(userId).stream()
                .map(categoryDtoConverter::convert)
                .collect(toList());
    }
}