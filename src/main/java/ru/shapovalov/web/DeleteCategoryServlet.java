package ru.shapovalov.web;

import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.shapovalov.SpringContext.getContext;

public class DeleteCategoryServlet extends BaseServlet {
    private final CategoryService categoryService;

    public DeleteCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String id = req.getParameter("id");
        if (isNumeric(id)) {
            if (categoryService.delete(Integer.parseInt(id), userId)) {
                writer.write("Category deleted!");
            } else {
                writer.write("Delete failed");
            }
        } else {
            writer.write("Wrong format!");
        }
    }
}