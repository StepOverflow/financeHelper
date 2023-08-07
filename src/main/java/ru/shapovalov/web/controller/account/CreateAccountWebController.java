package ru.shapovalov.web.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.service.AccountService;
import ru.shapovalov.web.form.AccountForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class CreateAccountWebController {

    private final AccountService accountService;

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        return "account-create";
    }

    @PostMapping("/create")
    public String postCreateAccount(@ModelAttribute("accountForm") @Valid AccountForm accountForm,
                                    BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "account-create";
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        accountService.create(accountForm.getName(), userId);
        return "redirect:/accounts/list";
    }
}