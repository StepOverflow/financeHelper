package ru.shapovalov.web;


import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static ru.shapovalov.SpringContext.getContext;

public class CategoryServlet extends HttpServlet {
    private final CategoryService categoryService;

    public CategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        String action = req.getParameter("action");
        switch (action) {
            case "add":
                String name = req.getParameter("name");
                CategoryDto categoryDto = categoryService.create(name, userId);
                writer.write("New account created!");
                writer.write(categoryDto.toString());
            case "delete":
                int id = Integer.parseInt(req.getParameter("id"));
                boolean delete = categoryService.delete(id, userId);
                if (delete) {
                    writer.write("Category deleted!");
                }
            case "edit":
                id = Integer.parseInt(req.getParameter("id"));
                name = req.getParameter("name");
                CategoryDto resultDto = categoryService.edit(id, name, userId);

                writer.write("Edited!");
                writer.write(resultDto.toString());
            default:
                List<CategoryDto> categoryDtos = categoryService.getAll(userId);
                writer.write(categoryDtos.toString());
        }
    }
}
