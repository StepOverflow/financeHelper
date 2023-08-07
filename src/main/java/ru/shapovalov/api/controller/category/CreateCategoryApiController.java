package ru.shapovalov.api.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.category.CreateCategoryRequest;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.CategoryRepository;
import ru.shapovalov.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CreateCategoryApiController {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody @Valid CreateCategoryRequest request,
                                                 HttpServletRequest httpServletRequest) {
        Category category = new Category();
        category.setName(request.getName());

        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
        }
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);
        if (savedCategory != null) {
            return ok("Category created successfully");
        }
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create category");
    }
}