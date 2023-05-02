package ru.shapovalov.web;

import org.apache.commons.lang3.StringUtils;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import static ru.shapovalov.SpringContext.*;

public class AccountServlet extends HttpServlet {

    private final AccountService accountService;

    public AccountServlet() {
        this.accountService = getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        String action = req.getParameter("action");
        switch (action) {
            case "add":
                String name = req.getParameter("name");
                AccountDto accountDto = accountService.create(name, userId);
                writer.write("New account created!");
                writer.write(accountDto.toString());
                break;
            case "delete":
                String accountIdParam = req.getParameter("id");
                if (accountIdParam == null || StringUtils.isNumeric(accountIdParam)) {
                    writer.write("Wrong format!");
                } else {
                    int accountId = Integer.parseInt(accountIdParam);
                    boolean delete = accountService.delete(accountId, userId);
                    if (delete) {
                        writer.write("Account deleted!");
                    }
                }
                break;
            default:
                List<AccountDto> accountDtos = accountService.getAll(userId);
                writer.write(accountDtos.toString());
                break;
        }
    }
}



