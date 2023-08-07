package ru.shapovalov.api.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.transaction.TransferRequest;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.CategoryRepository;
import ru.shapovalov.repository.TransactionRepository;
import ru.shapovalov.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransferTransactionApiController {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody @Valid TransferRequest request,
                                           HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
        }

        Account sourceAccount = accountRepository.findById(request.getFromAccountId()).orElse(null);
        Account destinationAccount = accountRepository.findById(request.getToAccountId()).orElse(null);

        int amount = request.getSum();

        if (sourceAccount == null || destinationAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Source or destination account not found");
        }

        if (sourceAccount.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds in the source account");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() + amount);
        destinationAccount.setBalance(destinationAccount.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setAmountPaid(amount);
        transaction.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        transaction.setFromAccount(sourceAccount);
        transaction.setToAccount(destinationAccount);
        transaction.setCategories(categoryRepository.findByIdIn(request.getCategoryIds()));
        transactionRepository.save(transaction);

        return ResponseEntity.ok("Transfer successful");
    }
}
