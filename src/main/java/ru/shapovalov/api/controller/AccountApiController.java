package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.json.account.*;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;
import ru.shapovalov.service.UserService;

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

    @GetMapping("/list")
    public ResponseEntity<List<AccountResponse>> getAllByUserId() {
        Long userId = userService.currentUser().getId();

        List<AccountResponse> accountResponses = accountService.findAllByUserId(userId).stream()
                .map(account -> new AccountResponse(account.getId(), account.getAccountName(), account.getBalance()))
                .collect(Collectors.toList());

        return ok(accountResponses);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid CreateAccountRequest request) {
        Long userId = userService.currentUser().getId();

        AccountDto accountDto = accountService.create(request.getName(), userId);

        if (accountDto != null) {
            return ok(new AccountResponse(accountDto.getId(), accountDto.getAccountName(), accountDto.getBalance()));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteAccountResponse> delete(@RequestBody @Valid AccountIdRequest request) {
        Long userId = userService.currentUser().getId();
        boolean delete = accountService.delete(request.getAccountId(), userId);

        if (delete) {
            return ok(new DeleteAccountResponse(true));
        } else {
            return ok(new DeleteAccountResponse(false));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<EditAccountResponse> edit(@RequestBody @Valid EditAccountRequest request) {
        Long userId = userService.currentUser().getId();
        boolean edit = accountService.edit(request.getId(), request.getName(), userId);

        if (edit) {
            return ok(new EditAccountResponse(request.getId(), request.getName()));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}