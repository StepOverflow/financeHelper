package ru.shapovalov.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.category.DeleteCategoryRequest;
import ru.shapovalov.json.category.DeleteCategoryResponse;
import ru.shapovalov.service.CategoryService;

@Controller("/categories/delete")
@RequiredArgsConstructor
public class DeleteCategoryController implements SecureController<DeleteCategoryRequest, DeleteCategoryResponse> {
    private final CategoryService categoryService;

    @Override
    public DeleteCategoryResponse handle(DeleteCategoryRequest request, Integer userId) {
        return new DeleteCategoryResponse(categoryService.delete(request.getId(), userId));
    }

    @Override
    public Class<DeleteCategoryRequest> getRequestClass() {
        return DeleteCategoryRequest.class;
    }
}