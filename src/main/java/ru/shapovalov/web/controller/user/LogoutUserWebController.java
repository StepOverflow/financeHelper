package ru.shapovalov.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutUserWebController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout-success";
    }
}