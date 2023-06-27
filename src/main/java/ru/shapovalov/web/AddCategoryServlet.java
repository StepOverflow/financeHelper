package ru.shapovalov.web;

import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static ru.shapovalov.SpringContext.getContext;

public class AddCategoryServlet extends BaseServlet {
    private final CategoryService categoryService;

    public AddCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String name = req.getParameter("name");

        if (isNotEmpty(name)) {
            CategoryDto categoryDto = categoryService.create(name, userId);
            writer.write("New account created!");
            writer.write(categoryDto.toString());
        } else {
            writer.write("Wrong format!");
        }
    }
}