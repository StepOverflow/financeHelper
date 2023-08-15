package ru.shapovalov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.service.AccountService;
import ru.shapovalov.web.form.AccountForm;
import ru.shapovalov.web.form.DeleteAccountForm;
import ru.shapovalov.web.form.EditAccountForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountWebController {
    private final AccountService accountService;

    @GetMapping("/list")
    public String listAccounts(Model model, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        model.addAttribute("accounts", accountService.getAll(userId));

        return "account-list";
    }
    @GetMapping("/create")
    public String getCreateForm(Model model, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        model.addAttribute("accountForm", new AccountForm());
        return "account-create";
    }

    @PostMapping("/create")
    public String postCreateAccount(@ModelAttribute("accountForm") @Valid AccountForm accountForm, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        accountService.create(accountForm.getName(), userId);
        return "redirect:/accounts/list";
    }

    @GetMapping("/delete")
    public String getDeleteAccount(Model model, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        model.addAttribute("form", new DeleteAccountForm());
        return "account-delete";
    }

    @PostMapping("/delete")
    public String postDeleteAccount(@ModelAttribute("form") @Valid DeleteAccountForm form, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        accountService.delete(form.getAccountId(), userId);
        return "redirect:/accounts/list";
    }

    @GetMapping("/edit")
    public String getEditAccount(Model model, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        model.addAttribute("accountForm", new EditAccountForm());
        return "account-edit";
    }

    @PostMapping("/edit")
    public String postEditAccount(@ModelAttribute("accountForm") @Valid EditAccountForm form, HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId == null) return "redirect:/login";

        accountService.edit(form.getAccountId(), form.getName(), userId);
        return "redirect:/accounts/list";
    }

    private static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Long) session.getAttribute("userId");
    }
}