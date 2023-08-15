package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.json.category.*;
import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllByUserId(HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);

        List<CategoryResponse> categoryResponses = categoryService.getAll(userId).stream()
                .map(categoryDto -> new CategoryResponse(categoryDto.getId(), categoryDto.getName()))
                .collect(Collectors.toList());
        return ok(categoryResponses);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request,
                                                   HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        CategoryDto categoryDto = categoryService.create(request.getName(), userId);
        if (categoryDto != null) {
            return ok(new CategoryResponse(categoryDto.getId(), categoryDto.getName()));
        }
        return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteCategoryResponse> delete(@RequestBody CategoryIdRequest request,
                                                         HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        boolean delete = categoryService.delete(request.getCategoryId(), userId);
        if (delete) {
            return ok(new DeleteCategoryResponse(true));
        } else {
            return ok(new DeleteCategoryResponse(false));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<EditCategoryResponse> edit(@RequestBody @Valid EditCategoryRequest request,
                                                     HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        CategoryDto edit = categoryService.edit(request.getId(), request.getName(), userId);
        if (edit != null) {
            return ok(new EditCategoryResponse(request.getId(), request.getName()));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Long getSessionUserId(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        return userId;
    }
}