package ru.shapovalov.service;

import org.springframework.stereotype.Service;
import ru.shapovalov.converter.Converter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.dao.CategoryModel;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final Converter<CategoryModel, CategoryDto> categoryDtoConverter;

    public CategoryService(CategoryDao categoryDao, Converter<CategoryModel, CategoryDto> categoryDtoConverter) {
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
    public List<CategoryDto> getAll(int userId) {
        return categoryDao.getAllByUserId(userId).stream()
                .map(categoryDtoConverter::convert)
                .collect(toList());
    }
}