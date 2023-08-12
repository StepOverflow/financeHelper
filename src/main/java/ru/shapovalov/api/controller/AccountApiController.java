package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.json.account.AccountIdRequest;
import ru.shapovalov.api.json.account.AccountResponse;
import ru.shapovalov.api.json.account.CreateAccountRequest;
import ru.shapovalov.api.json.account.EditAccountRequest;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<List<AccountResponse>> getUserAccounts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<AccountResponse> accountResponses = accountService.findAccountsByUserId(userId).stream()
                .map(account -> new AccountResponse(account.getId(), account.getAccountName(), account.getBalance()))
                .collect(Collectors.toList());

        return ok(accountResponses);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody @Valid CreateAccountRequest request,
                                                HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        UserDto userDto = userService.findById(userId);
        if (userDto == null) {
            return status(HttpStatus.BAD_REQUEST).body("Invalid user ID");
        }

        AccountDto accountDto = accountService.create(request.getName(), userId);

        if (accountDto != null) {
            return ok("Account created successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid AccountIdRequest request,
                                                HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        boolean delete = accountService.delete(request.getAccountId(), userId);

        if (delete) {
            return ok("Account deleted successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete account");
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editAccount(@RequestBody @Valid EditAccountRequest request,
                                              HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        boolean edit = accountService.edit(request.getId(), request.getName(), userId);

        if (edit) {
            return ok("Account edited successfully");
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to edit account");
        }
    }
}