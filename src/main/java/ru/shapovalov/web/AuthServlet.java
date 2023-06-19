package ru.shapovalov.web;

import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static ru.shapovalov.SpringContext.getContext;

public class AuthServlet extends BaseServlet {
    private final UserAuthService userAuthService;

    public AuthServlet() {
        this.userAuthService = getContext().getBean(UserAuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<UserDto> userDtoOptional = userAuthService.auth(login, password);
        UserDto user = userDtoOptional.get();

        PrintWriter writer = resp.getWriter();
        if (user == null) {
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write(user.toString());
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
        }
    }
}