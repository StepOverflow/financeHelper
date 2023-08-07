package ru.shapovalov.api.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.TransactionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionApiController {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @PostMapping("/user")
    public ResponseEntity<List<Transaction>> getUserTransactions(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Account> userAccounts = accountRepository.findAccountsByUserId(userId);
        List<Long> accountIds = userAccounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());
        List<Transaction> userTransactions = transactionRepository.findByFromAccountIdInOrToAccountIdIn(accountIds, accountIds);

        return ResponseEntity.ok(userTransactions);
    }
}
