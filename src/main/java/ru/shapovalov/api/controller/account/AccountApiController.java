package ru.shapovalov.api.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.account.AccountsResponse;
import ru.shapovalov.entity.Account;
import ru.shapovalov.repository.AccountRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountRepository accountRepository;

    @PostMapping("/user")
    public ResponseEntity<AccountsResponse> getUserAccounts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Account> accounts = accountRepository.findAccountsByUserId(userId);
        return ok(new AccountsResponse(userId, accounts));
    }
}