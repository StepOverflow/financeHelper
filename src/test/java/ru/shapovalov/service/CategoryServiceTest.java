package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.shapovalov.api.converter.CategoryModelToCategoryDtoConverter;
import ru.shapovalov.dao.CategoryDao;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.CategoryRepository;
import ru.shapovalov.repository.UserRepository;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private CategoryModelToCategoryDtoConverter categoryDtoConverter;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCreateCategory() {
        Long userId = 1L;
        String categoryName = "New Category";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());
        when(categoryDtoConverter.convert(any(Category.class))).thenReturn(new CategoryDto());

        CategoryDto categoryDto = categoryService.create(categoryName, userId);

        assertNotNull(categoryDto);
        verify(userRepository, times(1)).findById(userId);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryDtoConverter, times(1)).convert(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        Long categoryId = 1L;
        Long userId = 1L;
        Category category = new Category();
        category.setUser(new User());
        category.getUser().setId(userId);

        when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenReturn(category);

        boolean result = categoryService.delete(categoryId, userId);

        assertTrue(result);
        verify(categoryRepository, times(1)).findByIdAndUserId(categoryId, userId);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testEditCategory() {
        Long categoryId = 1L;
        Long userId = 1L;
        String newCategoryName = "Updated Category";
        Category category = new Category();
        category.setUser(new User());
        category.getUser().setId(userId);

        when(categoryRepository.findByIdAndUserId(categoryId, userId)).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryDtoConverter.convert(any(Category.class))).thenReturn(new CategoryDto());

        CategoryDto updatedCategoryDto = categoryService.edit(categoryId, newCategoryName, userId);

        assertNotNull(updatedCategoryDto);
        assertEquals(newCategoryName, category.getName());
        verify(categoryRepository, times(1)).findByIdAndUserId(categoryId, userId);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryDtoConverter, times(1)).convert(any(Category.class));
    }
}