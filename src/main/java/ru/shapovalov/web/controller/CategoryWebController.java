package ru.shapovalov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.service.CategoryService;
import ru.shapovalov.web.form.CreateCategoryForm;
import ru.shapovalov.web.form.DeleteCategoryForm;
import ru.shapovalov.web.form.EditCategoryForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryWebController extends BaseWebController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getCategoryList(Model model, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        model.addAttribute("categories", categoryService.getAll(userId));

        return "category-list";
    }

    @GetMapping("/create")
    public String getCreateCategory(Model model, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        model.addAttribute("categoryForm", new CreateCategoryForm());
        return "category-create";
    }

    @PostMapping("/create")
    public String postCreateCategory(@ModelAttribute("categoryForm") @Valid CreateCategoryForm form, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        categoryService.create(form.getName(), userId);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete")
    public String getDeleteCategory(Model model, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        model.addAttribute("categoryForm", new DeleteCategoryForm());
        return "category-delete";
    }

    @PostMapping("/delete")
    public String postDeleteCategory(@ModelAttribute("categoryForm") @Valid DeleteCategoryForm form, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        categoryService.delete(form.getCategoryId(), userId);
        return "redirect:/categories/list";
    }

    @GetMapping("/edit")
    public String getEditCategory(Model model, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        model.addAttribute("categoryForm", new EditCategoryForm());
        return "category-edit";
    }

    @PostMapping("/edit")
    public String postEditCategory(@ModelAttribute("categoryForm") @Valid EditCategoryForm form, HttpServletRequest request) {
        Long userId = getSessionUserId(request);
        if (userId == null) return "redirect:/login";
        categoryService.edit(form.getCategoryId(), form.getName(), userId);
        return "redirect:/categories/list";
    }
}
