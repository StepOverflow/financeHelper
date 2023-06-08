package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.dao.CategoryModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    @InjectMocks
    CategoryService subj;

    @Mock
    CategoryDao categoryDao;

    @Mock
    CategoryModelToCategoryDtoConverter categoryDtoConverter;

    @Test
    public void create_success() {
        String categoryName = "newCategory";
        int userId = 1;

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryName);
        categoryModel.setUserId(userId);

        CategoryDto expectedCategoryDto = new CategoryDto();
        expectedCategoryDto.setId(categoryModel.getId());
        expectedCategoryDto.setName(categoryModel.getName());
        expectedCategoryDto.setUserId(categoryModel.getUserId());

        when(categoryDao.insert(categoryName, userId)).thenReturn(categoryModel);
        when(categoryDtoConverter.convert(categoryModel)).thenReturn(expectedCategoryDto);

        CategoryDto actualCategoryDto = subj.create(categoryName, userId);

        assertEquals(expectedCategoryDto, actualCategoryDto);

        verify(categoryDao, times(1)).insert(categoryName, userId);
        verify(categoryDtoConverter, times(1)).convert(categoryModel);
    }

    @Test
    public void delete_categoryDeleted() {
        int id = 1;
        int userId = 1;

        when(categoryDao.delete(id, userId)).thenReturn(true);

        assertTrue(categoryDao.delete(id, userId));

        verify(categoryDao, times(1)).delete(id, userId);
    }

    @Test
    public void delete_categoryNotDeleted() {
        int id = 1;
        int userId = 1;

        when(categoryDao.delete(id, userId)).thenReturn(false);

        assertFalse(categoryDao.delete(id, userId));

        verify(categoryDao, times(1)).delete(id, userId);
    }

    @Test
    public void edit() {
        int id = 1;
        String name = "newName";
        int userId = 1;

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(id);
        categoryModel.setName(name);
        categoryModel.setUserId(userId);

        CategoryDto exceptedCategoryDto = new CategoryDto();
        exceptedCategoryDto.setId(categoryModel.getId());
        exceptedCategoryDto.setName(categoryModel.getName());
        exceptedCategoryDto.setUserId(categoryModel.getUserId());

        when(categoryDao.edit(id, name, userId)).thenReturn(categoryModel);
        when(categoryDtoConverter.convert(categoryModel)).thenReturn(exceptedCategoryDto);

        CategoryDto actualCategoryDto = subj.edit(id, name, userId);

        assertEquals(exceptedCategoryDto, actualCategoryDto);

        verify(categoryDao, times(1)).edit(id, name, userId);
        verify(categoryDtoConverter, times(1)).convert(categoryModel);
    }
}