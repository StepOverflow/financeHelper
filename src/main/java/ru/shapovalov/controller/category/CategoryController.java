package ru.shapovalov.controller.category;

import ru.shapovalov.json.category.CategoryRequest;
import ru.shapovalov.json.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.service.CategoryService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller("/categories")
@RequiredArgsConstructor
public class CategoryController implements SecureController<CategoryRequest, List<CategoryResponse>> {
    private final CategoryService categoryService;

    @Override
    public List<CategoryResponse> handle(CategoryRequest request, Integer userId) {
        return categoryService.getAll(userId).stream()
                .map(categoryDto -> new CategoryResponse(categoryDto.getId(), categoryDto.getName()))
                .collect(toList());
    }

    @Override
    public Class<CategoryRequest> getRequestClass() {
        return CategoryRequest.class;
    }
}