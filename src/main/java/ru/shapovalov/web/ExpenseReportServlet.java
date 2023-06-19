package ru.shapovalov.web;

import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static ru.shapovalov.SpringContext.getContext;

public class ExpenseReportServlet extends BaseServlet {
    private final CategoryService categoryService;

    public ExpenseReportServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req);

        int days = Integer.parseInt(req.getParameter("days"));
        Map<String, Integer> resultExpenseInPeriodByCategory = categoryService.getResultExpenseInPeriodByCategory(userId, days);
        writer.write(resultExpenseInPeriodByCategory.toString());
    }
}