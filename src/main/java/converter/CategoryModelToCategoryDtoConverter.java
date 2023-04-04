package converter;

import dao.CategoryModel;
import service.AccountDto;
import service.CategoryDto;

public class CategoryModelToCategoryDtoConverter implements Converter<CategoryModel, CategoryDto>{
    @Override
    public CategoryDto convert(CategoryModel source) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(source.getName());
        categoryDto.setId(source.getId());
        categoryDto.setUserId(source.getUserId());
        return categoryDto;
    }
}
