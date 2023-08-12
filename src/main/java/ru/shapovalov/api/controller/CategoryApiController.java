package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.json.category.CategoriesResponse;
import ru.shapovalov.api.json.category.CategoryIdRequest;
import ru.shapovalov.api.json.category.CreateCategoryRequest;
import ru.shapovalov.api.json.category.EditCategoryRequest;
import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<CategoriesResponse> getAllCategoriesByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<CategoryDto> categories = categoryService.getAll(userId);
        return ok(new CategoriesResponse(userId, categories));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CreateCategoryRequest request,
                                                 HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        UserDto userDto = userService.findById(userId);
        if (userDto == null) {
            return status(HttpStatus.BAD_REQUEST).body("Invalid userDto ID");
        }
        CategoryDto categoryDto = categoryService.create(request.getName(), userId);

        if (categoryDto != null) {
            return ok("Category created successfully");
        }
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create category");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestBody CategoryIdRequest request,
                                                 HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        boolean delete = categoryService.delete(request.getCategoryId(), userId);
        if (delete) {
            return ok("Category deleted successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete category");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editCategory(
            @RequestBody @Valid EditCategoryRequest request,
            HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        CategoryDto edit = categoryService.edit(request.getId(), request.getName(), userId);
        if (edit != null) {
            return ok("Category deleted successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete category");
        }
    }
}