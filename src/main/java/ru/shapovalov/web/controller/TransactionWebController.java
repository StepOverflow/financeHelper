package ru.shapovalov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.service.CategoryService;
import ru.shapovalov.service.TransactionDto;
import ru.shapovalov.service.TransactionService;
import ru.shapovalov.service.UserService;
import ru.shapovalov.web.form.ReportForm;
import ru.shapovalov.web.form.TransferForm;

import javax.validation.Valid;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionWebController {

    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/transfer")
    public String getTransfer() {
        Long userId = userService.currentUser().getId();
        if (userId == null) return "redirect:/login";
        return "transaction-transfer";
    }

    @PostMapping("/transfer")
    public String postTransfer(@ModelAttribute("transferForm") @Valid TransferForm transferForm, Model model) {
        Long userId = userService.currentUser().getId();
        if (userId == null) return "redirect:/login";
        TransactionDto transactionDto = transactionService.sendMoney(transferForm.getFromAccountId(), transferForm.getToAccountId(), transferForm.getSum(), userId, transferForm.getCategoryIds());

        model.addAttribute("transaction", transactionDto);

        return "transaction-receipt";
    }

    @GetMapping("/expense")
    public String expenseReport(@ModelAttribute("reportForm") @Valid ReportForm reportForm, Model model) {
        Long userId = userService.currentUser().getId();
        if (userId == null) return "redirect:/login";
        model.addAttribute("report", categoryService.getResultExpenseInPeriodByCategory(userId, reportForm.getDays()));

        return "transaction-expense";
    }

    @GetMapping("/income")
    public String incomeReport(@ModelAttribute("reportForm") @Valid ReportForm reportForm, Model model) {
        Long userId = userService.currentUser().getId();
        if (userId == null) return "redirect:/login";
        model.addAttribute("report", categoryService.getResultIncomeInPeriodByCategory(userId, reportForm.getDays()));

        return "transaction-income";
    }
}