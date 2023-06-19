package ru.shapovalov.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseServlet extends HttpServlet {
    public Integer getUserId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (Integer) session.getAttribute("userId");
    }
}