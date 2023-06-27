package ru.shapovalov.web;

import org.apache.commons.lang3.StringUtils;
import ru.shapovalov.service.CategoryDto;
import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.shapovalov.SpringContext.getContext;

public class EditCategoryServlet extends BaseServlet {
    private final CategoryService categoryService;

    public EditCategoryServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        if (isNumeric(id) && StringUtils.isNotEmpty(name)) {
            CategoryDto resultDto = categoryService.edit(Integer.parseInt(id), name, userId);

            writer.write("Edited!");
            writer.write(resultDto.toString());
        } else {
            writer.write("Wrong format!");
        }
    }
}