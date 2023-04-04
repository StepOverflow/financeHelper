package service;

import converter.CategoryModelToCategoryDtoConverter;
import dao.CategoryDao;
import dao.CategoryModel;
import dao.UserDao;

public class CategoryService {
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final CategoryModelToCategoryDtoConverter categoryDtoConverter;

    public CategoryService() {
        categoryDtoConverter = new CategoryModelToCategoryDtoConverter();
        categoryDao = new CategoryDao();
        userDao = new UserDao();
    }

    public CategoryDto createCategory(String categoryName, int userId) {
        CategoryModel categoryModel = categoryDao.insert(categoryName, userId);
        if (categoryModel == null) {
            return null;
        }
        return categoryDtoConverter.convert(categoryModel);
    }

    public boolean deleteCategory(int categoryId, int userId) {
        return categoryDao.delete(categoryId, userId);
    }

    public CategoryDto editCategory(int categoryId, String newCategoryName, int userId) {
        CategoryModel categoryModel = categoryDao.edit(categoryId, newCategoryName, userId);
        if (categoryModel == null) {
            return null;
        }
        return categoryDtoConverter.convert(categoryModel);
    }
}
