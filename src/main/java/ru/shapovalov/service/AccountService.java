package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.converter.AccountToAccountDtoConverter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.entity.Account;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;
    private final AccountToAccountDtoConverter accountDtoConverter;

    public List<AccountDto> getAll(int userId) {
        return accountDao.getAllByUserId(userId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    public AccountDto create(String accountName, int userId) {
        Account account = accountDao.insert(accountName, userId);
        return accountDtoConverter.convert(account);
    }

    public boolean edit(int accountId, String newName, int userId) {
        return accountDao.edit(accountId, newName, userId);
    }

    public boolean delete(int accountId, int userId) {
        return accountDao.delete(accountId, userId);
    }
}