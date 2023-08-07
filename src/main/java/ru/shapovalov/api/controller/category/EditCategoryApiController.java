package ru.shapovalov.api.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.category.EditCategoryRequest;
import ru.shapovalov.entity.Category;
import ru.shapovalov.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class EditCategoryApiController {
    private final CategoryRepository categoryRepository;

    @PostMapping("/edit")
    public ResponseEntity<Category> editCategory(
            @RequestBody @Valid EditCategoryRequest request,
            HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Category category = categoryRepository.findByIdAndUserId(request.getId(), userId);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        category.setName(request.getName());
        Category updatedCategory = categoryRepository.save(category);

        return ResponseEntity.ok(updatedCategory);
    }
}