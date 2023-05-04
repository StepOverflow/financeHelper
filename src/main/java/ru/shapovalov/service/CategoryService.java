package ru.shapovalov.service;

import ru.shapovalov.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.dao.CategoryModel;

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
}