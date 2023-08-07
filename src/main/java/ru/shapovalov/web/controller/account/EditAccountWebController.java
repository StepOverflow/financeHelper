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
import ru.shapovalov.web.form.EditAccountForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class EditAccountWebController {

    private final AccountService accountService;

    @GetMapping("/edit")
    public String getEditAccount(Model model) {
        model.addAttribute("accountForm", new EditAccountForm());
        return "account-edit";
    }

    @PostMapping("/edit")
    public String postEditAccount(@ModelAttribute("accountForm") @Valid EditAccountForm form,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        accountService.edit(form.getAccountId(), form.getName(), userId);
        return "redirect:/accounts/list";

    }
}