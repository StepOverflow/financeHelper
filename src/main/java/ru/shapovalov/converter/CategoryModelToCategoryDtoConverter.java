package ru.shapovalov.converter;

import ru.shapovalov.entity.Category;
import org.springframework.stereotype.Service;
import ru.shapovalov.service.CategoryDto;

@Service
public class CategoryModelToCategoryDtoConverter implements Converter<Category, CategoryDto> {
    @Override
    public CategoryDto convert(Category source) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(source.getName());
        categoryDto.setId(source.getId());
        categoryDto.setUserId(source.getUser().getId());
        return categoryDto;
    }
}