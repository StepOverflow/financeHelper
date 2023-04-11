package ru.shapovalov.service;

import ru.shapovalov.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.dao.CategoryModel;
import ru.shapovalov.dao.UserDao;

public class CategoryService {
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public CategoryService() {
        categoryDtoConverter = new CategoryModelToCategoryDtoConverter();
        categoryDao = new CategoryDao();
        userDao = new UserDao();
    }

    public CategoryDto create(String categoryName, int userId) {
        CategoryModel categoryModel = categoryDao.insert(categoryName, userId);
        return categoryDtoConverter.convert(categoryModel);
    }

    public boolean delete(String name, int userId) {
        return categoryDao.delete(name, userId);
    }

    public CategoryDto edit(String name, String newCategoryName, int userId) {
        CategoryModel categoryModel = categoryDao.edit(name, newCategoryName, userId);
        return categoryDtoConverter.convert(categoryModel);
    }
}