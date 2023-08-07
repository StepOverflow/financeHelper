package ru.shapovalov.api.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.category.CategoriesResponse;
import ru.shapovalov.entity.Category;
import ru.shapovalov.repository.CategoryRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApiController {
    private final CategoryRepository categoryRepository;

    @PostMapping("/user")
    public ResponseEntity<CategoriesResponse> getAllCategoriesByUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Category> categories = categoryRepository.findByUserId(userId);
        return ResponseEntity.ok(new CategoriesResponse(userId, categories));
    }
}