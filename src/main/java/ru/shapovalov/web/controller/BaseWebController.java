package ru.shapovalov.web.controller;

import ru.shapovalov.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseWebController {
    protected Long getSessionUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException("User not logged in");
        }
        return userId;
    }
}
