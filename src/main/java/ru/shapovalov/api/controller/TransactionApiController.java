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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/list")
    public ResponseEntity<List<TransactionDto>> getUserTransactions(HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        List<AccountDto> userAccounts = accountService.getAll(userId);
        List<Long> accountIds = userAccounts.stream()
                .map(AccountDto::getId)
                .collect(Collectors.toList());
        List<TransactionDto> userTransactions = transactionService.findByFromAccountIdInOrToAccountIdIn(accountIds, accountIds);

        return ok(userTransactions);
    }

    @PostMapping("/user/income")
    public ResponseEntity<Map<String, Long>> getUserIncomeInPeriod(@RequestBody @Valid ReportRequest request,
                                                                   HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        Map<String, Long> income = categoryService.getResultIncomeInPeriodByCategory(userId, request.getDays());

        return ok(income);
    }

    @PostMapping("/user/expense")
    public ResponseEntity<Map<String, Long>> getUserExpenseInPeriod(@RequestBody @Valid ReportRequest request,
                                                                    HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        Map<String, Long> expense = categoryService.getResultExpenseInPeriodByCategory(userId, request.getDays());

        return ok(expense);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferRequest request,
                                           HttpServletRequest httpServletRequest) {

        Long userId = getSessionUserId(httpServletRequest);
        TransactionDto transactionDto = transactionService.sendMoney(request.getFromAccountId(), request.getToAccountId(), request.getSum(), userId, request.getCategoryIds());

        if (transactionDto != null) {
            return ok("Transfer successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to transfer");
        }
    }

    private Long getSessionUserId(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        return userId;
    }
}