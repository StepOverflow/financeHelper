package ru.shapovalov.api.json.account;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shapovalov.entity.Account;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AccountsResponse {
    private final Long userId;
    private final List<Account> accounts;
}