package ru.shapovalov.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.category.AddCategoryRequest;
import ru.shapovalov.json.category.AddCategoryResponse;
import ru.shapovalov.service.CategoryService;

import java.util.Optional;

@Controller("/categories/add")
@RequiredArgsConstructor
public class AddCategoryController implements SecureController<AddCategoryRequest, AddCategoryResponse> {
    private final CategoryService categoryService;

    @Override
    public AddCategoryResponse handle(AddCategoryRequest request, Integer userId) {
        return Optional.ofNullable(categoryService.create(request.getName(), userId)).map(categoryDto -> new AddCategoryResponse(categoryDto.getId(), categoryDto.getName())).orElse(null);
    }

    @Override
    public Class<AddCategoryRequest> getRequestClass() {
        return AddCategoryRequest.class;
    }
}