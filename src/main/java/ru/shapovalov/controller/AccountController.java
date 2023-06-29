package ru.shapovalov.controller;

import org.springframework.stereotype.Service;
import ru.shapovalov.json.AccountRequest;
import ru.shapovalov.json.AccountResponse;

@Service("/accounts")
public class AccountController implements SecureController<AccountRequest, AccountResponse> {

    @Override
    public AccountResponse handle(AccountRequest request, Integer userId) {
        return null;
    }

    @Override
    public Class<AccountRequest> getRequestClass() {
        return AccountRequest.class;
    }
}
