package ru.shapovalov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;
import ru.shapovalov.web.form.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserWebController {
    private final UserService authService;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        UserDto userDto = authService.getByUserId(userId);
        if (userDto == null) {
            session.removeAttribute("userId");
            return "redirect:/login";
        }
        model.addAttribute("email", userDto.getEmail()).addAttribute("id", userDto.getId()).addAttribute("userDto", userDto);
        return "login-success";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {

        model.addAttribute("form", new LoginForm());

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("form") @Valid LoginForm form, BindingResult result, Model model, HttpServletRequest request) {
        if (!result.hasErrors()) {
            Optional<UserDto> user = authService.auth(form.getEmail(), form.getPassword());
            if (user.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.get().getId());

                return "redirect:/";

            }

            result.addError(new FieldError("form", "email", "Неверный логин или пароль!"));
        }
        model.addAttribute("form", form);

        return "login";
    }

    @GetMapping("/register")
    public String getRegistration(Model model) {
        model.addAttribute("form", new LoginForm());
        return "register";
    }

    @PostMapping("/register")
    public String postRegistration(@ModelAttribute("form") @Valid LoginForm form, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            UserDto userDto = authService.registration(form.getEmail(), form.getPassword());
            if (userDto != null) {
                model.addAttribute("userDto", userDto);
                return "/register-success";
            }
            result.addError(new FieldError("form", "email", "Registration failed!"));
        }
        model.addAttribute("form", form);
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout-success";
    }
}