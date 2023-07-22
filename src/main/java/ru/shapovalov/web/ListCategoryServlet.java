package ru.shapovalov.web;

import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import static ru.shapovalov.SpringContext.getContext;

public class ListCategoryServlet extends BaseServlet {
    private final CategoryService categoryService;

    public ListCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        List<CategoryDto> categoryDtos = categoryService.getAll(userId);
        if (categoryDtos.isEmpty()) {
            writer.write("Categories not found!");
        } else {
            writer.write(categoryDtos.toString());
        }
    }
}