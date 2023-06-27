package ru.shapovalov.web;

import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static ru.shapovalov.SpringContext.getContext;

public class AddAccountServlet extends BaseServlet {

    private final AccountService accountService;

    public AddAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        String name = req.getParameter("name");

        if (isNotEmpty(name)) {
            AccountDto accountDto = accountService.create(name, userId);
            writer.write("New account created! ");
            writer.write(accountDto.toString());
        } else {
            writer.write("Wrong format!");
        }
    }
}