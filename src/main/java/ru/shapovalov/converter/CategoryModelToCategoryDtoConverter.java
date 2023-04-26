package ru.shapovalov.converter;

import org.springframework.stereotype.Service;
import ru.shapovalov.dao.CategoryModel;
import ru.shapovalov.service.CategoryDto;
@Service
public class CategoryModelToCategoryDtoConverter implements Converter<CategoryModel, CategoryDto> {
    @Override
    public CategoryDto convert(CategoryModel source) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(source.getName());
        categoryDto.setId(source.getId());
        categoryDto.setUserId(source.getUserId());
        return categoryDto;
    }
}