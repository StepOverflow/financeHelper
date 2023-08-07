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
import ru.shapovalov.web.form.EditCategoryForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class EditCategoryWebController {

    private final CategoryService categoryService;

    @GetMapping("/edit")
    public String getEditCategory(Model model) {
        model.addAttribute("categoryForm", new EditCategoryForm());
        return "category-edit";
    }

    @PostMapping("/edit")
    public String postEditCategory(@ModelAttribute("categoryForm") @Valid EditCategoryForm form,
                                   HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        categoryService.edit(form.getCategoryId(), form.getName(), userId);
        return "redirect:/categories/list";
    }
}