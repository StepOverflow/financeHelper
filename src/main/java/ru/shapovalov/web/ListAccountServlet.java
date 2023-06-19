package ru.shapovalov.web;

import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static ru.shapovalov.SpringContext.getContext;

public class ListAccountServlet extends BaseServlet {

    private final AccountService accountService;

    public ListAccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req);

        List<AccountDto> accountDtos = accountService.getAll(userId);
        writer.write(accountDtos.toString());
    }
}