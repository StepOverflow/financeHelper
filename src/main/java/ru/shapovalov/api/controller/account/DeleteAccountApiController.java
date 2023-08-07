package ru.shapovalov.api.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.account.AccountIdRequest;
import ru.shapovalov.entity.Account;
import ru.shapovalov.repository.AccountRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class DeleteAccountApiController {
    private final AccountRepository accountRepository;

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid AccountIdRequest request,
                                                HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Long accountId = request.getAccountId();

        Account account = accountRepository.findByIdAndUserId(accountId, userId);
        if (account == null) {
            return status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        accountRepository.delete(account);

        return ok("Account deleted successfully");
    }
}