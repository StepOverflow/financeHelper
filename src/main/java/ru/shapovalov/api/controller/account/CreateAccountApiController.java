package ru.shapovalov.api.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.account.CreateAccountRequest;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class CreateAccountApiController {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody @Valid CreateAccountRequest request,
                                                HttpServletRequest httpServletRequest) {
        Account account = new Account();
        account.setName(request.getName());
        account.setBalance(0);

        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
        }
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        if (savedAccount != null) {
            return ok("Account created successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account");
        }
    }
}