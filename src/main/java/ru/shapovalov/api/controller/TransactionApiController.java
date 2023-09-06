package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.transaction.ReportRequest;
import ru.shapovalov.api.json.transaction.TransferRequest;
import ru.shapovalov.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionApiController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping("/list")
    public ResponseEntity<List<TransactionDto>> getUserTransactions() {
        Long userId = userService.currentUser().getId();
        List<AccountDto> userAccounts = accountService.getAll(userId);
        List<Long> accountIds = userAccounts.stream()
                .map(AccountDto::getId)
                .collect(Collectors.toList());
        List<TransactionDto> userTransactions = transactionService.findByFromAccountIdInOrToAccountIdIn(accountIds, accountIds);

        return ok(userTransactions);
    }

    @PostMapping("/user/income")
    public ResponseEntity<Map<String, Long>> getUserIncomeInPeriod(@RequestBody @Valid ReportRequest request) {
        Long userId = userService.currentUser().getId();
        Map<String, Long> income = categoryService.getResultIncomeInPeriodByCategory(userId, request.getDays());

        return ok(income);
    }

    @PostMapping("/user/expense")
    public ResponseEntity<Map<String, Long>> getUserExpenseInPeriod(@RequestBody @Valid ReportRequest request) {
        Long userId = userService.currentUser().getId();
        Map<String, Long> expense = categoryService.getResultExpenseInPeriodByCategory(userId, request.getDays());

        return ok(expense);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferRequest request) {

        Long userId = userService.currentUser().getId();
        TransactionDto transactionDto = transactionService.sendMoney(request.getFromAccountId(), request.getToAccountId(), request.getSum(), userId, request.getCategoryIds());

        if (transactionDto != null) {
            return ok("Transfer successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to transfer");
        }
    }
}