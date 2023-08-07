package ru.shapovalov.web.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.TransactionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class InputTransactionWebController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @GetMapping("/input")
    public String listOutputTransactions(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Account> userAccounts = accountRepository.findAccountsByUserId(userId);
        List<Long> accountIds = userAccounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());
        List<Transaction> inputTransactions = transactionRepository.findByToAccountIdIn(accountIds);

        model.addAttribute("transactions", inputTransactions);

        return "transaction-input";
    }
}
