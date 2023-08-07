package ru.shapovalov.web.controller.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.service.CategoryService;
import ru.shapovalov.web.form.CreateCategoryForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CreateCategoryWebController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    public String getCreateCategory(Model model) {
        model.addAttribute("categoryForm", new CreateCategoryForm());
        return "category-create";
    }

    @PostMapping("/create")
    public String postCreateCategory(@ModelAttribute("categoryForm") @Valid CreateCategoryForm form, BindingResult result,
                                     HttpServletRequest request) {
        if (result.hasErrors()) {
            return "category-create";
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        categoryService.create(form.getName(), userId);
        return "redirect:/categories/list";
    }
}
