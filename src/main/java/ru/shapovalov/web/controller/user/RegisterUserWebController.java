package ru.shapovalov.web.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.web.form.LoginForm;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class RegisterUserWebController {
    private final UserAuthService authService;

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
}