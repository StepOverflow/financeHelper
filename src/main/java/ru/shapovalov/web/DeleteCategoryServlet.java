package ru.shapovalov.web;

import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.shapovalov.SpringContext.getContext;

public class DeleteCategoryServlet extends BaseServlet {
    private final CategoryService categoryService;

    public DeleteCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req);

        int id = Integer.parseInt(req.getParameter("id"));
        boolean delete = categoryService.delete(id, userId);
        if (delete) {
            writer.write("Category deleted!");
        }
    }
}