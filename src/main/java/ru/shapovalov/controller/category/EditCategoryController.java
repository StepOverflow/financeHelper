package ru.shapovalov.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.category.EditCategoryRequest;
import ru.shapovalov.json.category.EditCategoryResponse;
import ru.shapovalov.service.CategoryService;

@Controller("/categories/edit")
@RequiredArgsConstructor
public class EditCategoryController implements SecureController<EditCategoryRequest, EditCategoryResponse> {
    private final CategoryService categoryService;

    @Override
    public EditCategoryResponse handle(EditCategoryRequest request, Integer userId) {
        return new EditCategoryResponse(categoryService.edit(request.getId(), request.getName(), userId) != null);
    }

    @Override
    public Class<EditCategoryRequest> getRequestClass() {
        return EditCategoryRequest.class;
    }
}