package ru.shapovalov.web;

import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.shapovalov.SpringContext.getContext;

public class UserServlet extends HttpServlet {
    private final UserAuthService authService;

    public UserServlet() {
        this.authService = getContext().getBean(UserAuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            UserDto userDto = authService.getByUserId(userId);
            writer.write(userDto.toString());
        }
    }
}