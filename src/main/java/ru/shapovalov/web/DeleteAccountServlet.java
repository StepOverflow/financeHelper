package ru.shapovalov.web;

import ru.shapovalov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.shapovalov.SpringContext.getContext;

public class DeleteAccountServlet extends BaseServlet {

    private final AccountService accountService;

    public DeleteAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String accountId = req.getParameter("id");

        if (isNumeric(accountId)) {
            boolean delete = accountService.delete(Integer.parseInt(accountId), userId);
            if (delete) {
                writer.write("Account deleted!");
            }
        } else {
            writer.write("Wrong format!");
        }
    }
}