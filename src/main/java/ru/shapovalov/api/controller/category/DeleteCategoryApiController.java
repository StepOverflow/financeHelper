package ru.shapovalov.api.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.category.CategoryIdRequest;
import ru.shapovalov.entity.Category;
import ru.shapovalov.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class DeleteCategoryApiController {
    private final CategoryRepository categoryRepository;

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestBody CategoryIdRequest request,
                                                 HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Long categoryId = request.getCategoryId();

        Category category = categoryRepository.findByIdAndUserId(categoryId, userId);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        categoryRepository.delete(category);
        return ResponseEntity.ok("Category deleted successfully");
    }
}