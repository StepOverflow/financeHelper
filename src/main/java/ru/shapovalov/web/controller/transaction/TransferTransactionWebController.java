package ru.shapovalov.web.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.TransactionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransferTransactionWebController {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/transfer")
    public String getTransfer(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("userAccounts", accountRepository.findAccountsByUserId(userId));
        return "transaction-transfer";
    }

    @PostMapping("/transfer")
    public String postTransfer(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Long fromAccountId = Long.parseLong(request.getParameter("fromAccountId"));
        String toAccountNumber = request.getParameter("toAccountNumber");
        int sum = Integer.parseInt(request.getParameter("sum"));

        Account fromAccount = accountRepository.findById(fromAccountId).orElse(null);
        Account toAccount = accountRepository.findAccountById(Long.parseLong(toAccountNumber));

        if (fromAccount == null || toAccount == null) {
            return "redirect:/transactions/transfer";
        }

        if (fromAccount.getBalance() < sum) {
            return "redirect:/transactions/transfer";
        }

        fromAccount.setBalance(fromAccount.getBalance() - sum);
        toAccount.setBalance(toAccount.getBalance() + sum);

        Transaction transaction = new Transaction();
        transaction.setAmountPaid(sum);
        transaction.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transactionRepository.save(transaction);

        model.addAttribute("transaction", transaction);

        return "transaction-receipt";
    }
}