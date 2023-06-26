package ru.shapovalov.web;

import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
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
        PrintWriter writer = resp.getWriter();

        if (isNotEmpty(login) && isNotEmpty(password)) {
            Optional<UserDto> userDtoOptional = userAuthService.auth(login, password);
            if (userDtoOptional.isPresent()) {
                UserDto user = userDtoOptional.get();
                writer.write(user.toString());
                req.getSession()
                        .setAttribute("userId", user.getId());
            } else {
                writer.write("Access denied!");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            writer.write("Wrong format!");
        }
    }
}