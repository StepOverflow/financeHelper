package ru.shapovalov.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.account.AccountRequest;
import ru.shapovalov.json.account.AccountResponse;
import ru.shapovalov.service.AccountService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller("/accounts")
@RequiredArgsConstructor
public class AccountController implements SecureController<AccountRequest, List<AccountResponse>> {
    private final AccountService accountService;

    @Override
    public List<AccountResponse> handle(AccountRequest request, Integer userId) {
        return accountService.getAll(userId)
                .stream()
                .map(accountDto -> new AccountResponse(accountDto.getId(), accountDto.getAccountName(), accountDto.getBalance()))
                .collect(toList());
    }

    @Override
    public Class<AccountRequest> getRequestClass() {
        return AccountRequest.class;
    }
}