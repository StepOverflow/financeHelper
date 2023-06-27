package ru.shapovalov.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {
    public Integer getUserId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            PrintWriter writer = resp.getWriter();
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return userId;
    }
}