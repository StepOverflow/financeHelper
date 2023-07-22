package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.User;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.*;

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
        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setName(categoryName);
        category.setUser(user);

        CategoryDto expectedCategoryDto = new CategoryDto();
        expectedCategoryDto.setId(category.getId());
        expectedCategoryDto.setName(category.getName());
        expectedCategoryDto.setUserId(category.getUser().getId());

        when(categoryDao.insert(categoryName, user.getId())).thenReturn(category);
        when(categoryDtoConverter.convert(category)).thenReturn(expectedCategoryDto);

        CategoryDto actualCategoryDto = subj.create(categoryName, user.getId());

        assertEquals(expectedCategoryDto, actualCategoryDto);

        verify(categoryDao, times(1)).insert(categoryName, user.getId());
        verify(categoryDtoConverter, times(1)).convert(category);
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
        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setUser(user);

        CategoryDto exceptedCategoryDto = new CategoryDto();
        exceptedCategoryDto.setId(category.getId());
        exceptedCategoryDto.setName(category.getName());
        exceptedCategoryDto.setUserId(category.getUser().getId());

        when(categoryDao.edit(id, name, user.getId())).thenReturn(category);
        when(categoryDtoConverter.convert(category)).thenReturn(exceptedCategoryDto);

        CategoryDto actualCategoryDto = subj.edit(id, name, user.getId());

        assertEquals(exceptedCategoryDto, actualCategoryDto);

        verify(categoryDao, times(1)).edit(id, name, user.getId());
        verify(categoryDtoConverter, times(1)).convert(category);
    }
}