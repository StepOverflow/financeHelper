package ru.shapovalov.web;

import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.shapovalov.SpringContext.getContext;

public class RegistrationServlet extends HttpServlet {
    private final UserAuthService userAuthService;

    public RegistrationServlet() {
        this.userAuthService = getContext().getBean(UserAuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserDto userDto = userAuthService.registration(login, password);

        PrintWriter writer = resp.getWriter();
        writer.write("New user registered!");
        writer.write(userDto.toString());

    }
}