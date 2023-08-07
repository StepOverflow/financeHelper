package ru.shapovalov.api.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.account.EditAccountRequest;
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
public class EditAccountApiController {
    private final AccountRepository accountRepository;

    @PostMapping("/edit")
    public ResponseEntity<String> editAccount(@RequestBody @Valid EditAccountRequest request,
                                              HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        Account account = accountRepository.findByIdAndUserId(request.getId(), userId);
        if (account == null) {
            return status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        account.setName(request.getName());
        accountRepository.save(account);

        return ok("Account edited successfully");
    }
}