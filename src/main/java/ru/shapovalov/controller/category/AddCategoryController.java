package ru.shapovalov.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.category.AddCategoryRequest;
import ru.shapovalov.json.category.AddCategoryResponse;
import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;

import java.util.Optional;

@Controller("/categories/add")
@RequiredArgsConstructor
public class AddCategoryController implements SecureController<AddCategoryRequest, AddCategoryResponse> {
    private final CategoryService categoryService;

    @Override
    public AddCategoryResponse handle(AddCategoryRequest request, Integer userId) {
        Optional<CategoryDto> categoryDtoOptional = Optional.ofNullable(categoryService.create(request.getName(), userId));
        return categoryDtoOptional.map(categoryDto -> new AddCategoryResponse(categoryDto.getId(), categoryDto.getName())).orElse(null);
    }

    @Override
    public Class<AddCategoryRequest> getRequestClass() {
        return AddCategoryRequest.class;
    }
}