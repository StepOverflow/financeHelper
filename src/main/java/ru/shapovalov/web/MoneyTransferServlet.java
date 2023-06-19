package ru.shapovalov.web;

import ru.shapovalov.exception.CustomException;
import ru.shapovalov.service.TransactionDto;
import ru.shapovalov.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static ru.shapovalov.SpringContext.getContext;

public class MoneyTransferServlet extends BaseServlet {

    private final TransactionService transactionService;

    public MoneyTransferServlet() {
        this.transactionService = getContext().getBean(TransactionService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req);

        int fromAccount = Integer.parseInt(req.getParameter("fromAccount"));
        int toAccount = Integer.parseInt(req.getParameter("toAccount"));
        int amountPaid = Integer.parseInt(req.getParameter("amountPaid"));
        String categoryIdsParam = req.getParameter("categoryIds");
        List<Integer> categoryIds =
                stream(categoryIdsParam.split(","))
                        .map(Integer::parseInt)
                        .collect(toList());

        try {
            TransactionDto transactionDto = transactionService.sendMoney(fromAccount, toAccount, amountPaid, userId, categoryIds);
            writer.write("Transaction successful!\n");
            writer.write(transactionDto.toString());
        } catch (CustomException e) {
            writer.write("Transaction failed: " + e.getMessage());
        }
    }
}