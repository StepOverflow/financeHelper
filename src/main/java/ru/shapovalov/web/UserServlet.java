package ru.shapovalov.web;

import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.shapovalov.SpringContext.getContext;

public class UserServlet extends BaseServlet {
    private final UserAuthService authService;

    public UserServlet() {
        this.authService = getContext().getBean(UserAuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Integer userId = getUserId(req, resp);

        UserDto userDto = authService.getByUserId(userId);
        writer.write(userDto.toString());
    }
}