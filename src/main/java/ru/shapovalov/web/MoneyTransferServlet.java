package ru.shapovalov.web;

import ru.shapovalov.exception.CustomException;
import ru.shapovalov.service.TransactionDto;
import ru.shapovalov.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.shapovalov.SpringContext.getContext;

public class MoneyTransferServlet extends BaseServlet {

    private final TransactionService transactionService;

    public MoneyTransferServlet() {
        this.transactionService = getContext().getBean(TransactionService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String from = req.getParameter("from");
        String to = req.getParameter("to");
        String amount = req.getParameter("amount");
        String[] categoryIdsParam = req.getParameterValues("category");
        List<Integer> categoryIds = new ArrayList<>();

        if (isNumeric(from) && isNumeric(to) && isNumeric(amount)) {
            for (String s : categoryIdsParam) {
                if (isNumeric(s)) {
                    categoryIds.add(parseInt(s));
                } else {
                    writer.write("Invalid category ID");
                    return;
                }
            }
            try {
                TransactionDto transactionDto = transactionService.sendMoney(parseInt(from), parseInt(to), parseInt(amount), userId, categoryIds);
                writer.write("Transaction successful!\n");
                writer.write(transactionDto.toString());
            } catch (CustomException e) {
                writer.write("Transaction failed: " + e.getMessage());
            }
        }

    }
}