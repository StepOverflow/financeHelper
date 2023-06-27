package ru.shapovalov.web;

import ru.shapovalov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.shapovalov.SpringContext.getContext;

public class EditAccountServlet extends BaseServlet {

    private final AccountService accountService;

    public EditAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String accountIdParam = req.getParameter("id");
        String newName = req.getParameter("newName");
        if (isNumeric(accountIdParam) && isNotEmpty(newName)) {
            int accountId = Integer.parseInt(accountIdParam);
            boolean edit = accountService.edit(accountId, newName, userId);
            if (edit) {
                writer.write("Account edited!");
            }
        } else {
            writer.write("Wrong format!");
        }
    }
}