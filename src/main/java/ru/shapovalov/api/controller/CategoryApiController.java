package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.json.category.*;
import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;
import ru.shapovalov.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllByUserId() {
        Long userId = userService.currentUser().getId();

        List<CategoryResponse> categoryResponses = categoryService.getAll(userId).stream()
                .map(categoryDto -> new CategoryResponse(categoryDto.getId(), categoryDto.getName()))
                .collect(Collectors.toList());
        return ok(categoryResponses);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        Long userId = userService.currentUser().getId();
        CategoryDto categoryDto = categoryService.create(request.getName(), userId);
        if (categoryDto != null) {
            return ok(new CategoryResponse(categoryDto.getId(), categoryDto.getName()));
        }
        return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteCategoryResponse> delete(@RequestBody CategoryIdRequest request) {
        Long userId = userService.currentUser().getId();
        boolean delete = categoryService.delete(request.getCategoryId(), userId);
        if (delete) {
            return ok(new DeleteCategoryResponse(true));
        } else {
            return ok(new DeleteCategoryResponse(false));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<EditCategoryResponse> edit(@RequestBody @Valid EditCategoryRequest request) {
        Long userId = userService.currentUser().getId();
        CategoryDto edit = categoryService.edit(request.getId(), request.getName(), userId);
        if (edit != null) {
            return ok(new EditCategoryResponse(request.getId(), request.getName()));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}