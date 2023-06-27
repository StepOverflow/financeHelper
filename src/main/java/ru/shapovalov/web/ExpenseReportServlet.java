package ru.shapovalov.web;

import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.shapovalov.SpringContext.getContext;

public class ExpenseReportServlet extends BaseServlet {
    private final CategoryService categoryService;

    public ExpenseReportServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String days = req.getParameter("days");
        if (isNumeric(days)) {
            writer.write(
                    categoryService
                            .getResultExpenseInPeriodByCategory(userId, parseInt(days))
                            .toString());
        } else {
            writer.write("Wrong format!");
        }
    }
}