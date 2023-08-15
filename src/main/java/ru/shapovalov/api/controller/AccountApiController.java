package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.json.account.*;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;

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

    @PostMapping("/list")
    public ResponseEntity<List<AccountResponse>> getAllByUserId(HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);

        List<AccountResponse> accountResponses = accountService.findAllByUserId(userId).stream()
                .map(account -> new AccountResponse(account.getId(), account.getAccountName(), account.getBalance()))
                .collect(Collectors.toList());

        return ok(accountResponses);
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> create(@RequestBody @Valid CreateAccountRequest request,
                                                  HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);

        AccountDto accountDto = accountService.create(request.getName(), userId);

        if (accountDto != null) {
            return ok(new AccountResponse(accountDto.getId(), accountDto.getAccountName(), accountDto.getBalance()));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteAccountResponse> delete(@RequestBody @Valid AccountIdRequest request,
                                                        HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        boolean delete = accountService.delete(request.getAccountId(), userId);

        if (delete) {
            return ok(new DeleteAccountResponse(true));
        } else {
            return ok(new DeleteAccountResponse(false));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<EditAccountResponse> edit(@RequestBody @Valid EditAccountRequest request,
                                                    HttpServletRequest httpServletRequest) {
        Long userId = getSessionUserId(httpServletRequest);
        boolean edit = accountService.edit(request.getId(), request.getName(), userId);

        if (edit) {
            return ok(new EditAccountResponse(request.getId(), request.getName()));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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