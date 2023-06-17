package ru.shapovalov.web;

import ru.shapovalov.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static ru.shapovalov.SpringContext.getContext;

public class ReportServlet extends HttpServlet {
    private final CategoryService categoryService;

    public ReportServlet() {
        this.categoryService = getContext().getBean(CategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession();

        Integer userId = (Integer) session.getAttribute("userId");

        String action = req.getParameter("action");
        switch (action) {
            case "incomes":
                int days = Integer.parseInt(req.getParameter("days"));
                Map<String, Integer> resultIncomeInPeriodByCategory = categoryService.getResultIncomeInPeriodByCategory(userId, days);
                writer.write(resultIncomeInPeriodByCategory.toString());
                break;
            case "expenses":
                days = Integer.parseInt(req.getParameter("days"));
                Map<String, Integer> resultExpenseInPeriodByCategory = categoryService.getResultExpenseInPeriodByCategory(userId, days);
                writer.write(resultExpenseInPeriodByCategory.toString());
                break;
        }
    }
}