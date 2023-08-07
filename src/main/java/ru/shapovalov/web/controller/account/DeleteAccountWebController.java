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
import ru.shapovalov.web.form.DeleteAccountForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class DeleteAccountWebController {

    private final AccountService accountService;

    @GetMapping("/delete")
    public String getDeleteAccount(Model model) {
        model.addAttribute("form", new DeleteAccountForm());
        return "account-delete";
    }

    @PostMapping("/delete")
    public String postDeleteAccount(@ModelAttribute("form") @Valid DeleteAccountForm form,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        accountService.delete(form.getAccountId(), userId);
        return "redirect:/accounts/list";

    }
}