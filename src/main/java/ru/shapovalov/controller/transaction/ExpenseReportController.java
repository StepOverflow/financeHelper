package ru.shapovalov.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.transaction.ReportRequest;
import ru.shapovalov.json.transaction.ReportResponse;
import ru.shapovalov.service.CategoryService;

@Controller("/report/expense")
@RequiredArgsConstructor
public class ExpenseReportController implements SecureController<ReportRequest, ReportResponse> {
    private final CategoryService categoryService;

    @Override
    public ReportResponse handle(ReportRequest request, Integer userId) {
        return new ReportResponse(categoryService.getResultExpenseInPeriodByCategory(userId, request.getDays()));
    }

    @Override
    public Class<ReportRequest> getRequestClass() {
        return ReportRequest.class;
    }
}