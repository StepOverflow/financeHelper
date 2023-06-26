package ru.shapovalov.web;

import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
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
        PrintWriter writer = resp.getWriter();

        if (isNotEmpty(login) && isNotEmpty(password)) {
            UserDto userDto = userAuthService.registration(login, password);
            if (userDto == null) {
                writer.write("Registration failed!");
            } else {
                writer.write("New user registered!");
                writer.write(userDto.toString());
            }
        } else {
            writer.write("Wrong format!");
        }
    }
}