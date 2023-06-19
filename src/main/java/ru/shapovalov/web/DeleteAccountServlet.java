package ru.shapovalov.web;

import org.apache.commons.lang3.StringUtils;
import ru.shapovalov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.shapovalov.SpringContext.getContext;

public class DeleteAccountServlet extends BaseServlet {

    private final AccountService accountService;

    public DeleteAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req);

        String accountIdParam = req.getParameter("id");
        if (StringUtils.isNumeric(accountIdParam)) {
            int accountId = Integer.parseInt(accountIdParam);
            boolean delete = accountService.delete(accountId, userId);
            if (delete) {
                writer.write("Account deleted!");
            }
        } else {
            writer.write("Wrong format!");
        }
    }
}