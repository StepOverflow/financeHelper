package ru.shapovalov.service;

import ru.shapovalov.converter.AccountModelToAccountDtoConverter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.dao.AccountModel;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AccountService {
    private final AccountDao accountDao;
    private final AccountModelToAccountDtoConverter accountDtoConverter;

    public AccountService(AccountDao accountDao, AccountModelToAccountDtoConverter accountDtoConverter) {
        this.accountDao = accountDao;
        this.accountDtoConverter = accountDtoConverter;
    }

    public List<AccountDto> getAll(int userId) {
        return accountDao.getAllByUserId(userId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    public AccountDto create(String accountName, int userId) {
        AccountModel accountModel = accountDao.insert(accountName, userId);
        return accountDtoConverter.convert(accountModel);
    }

    public boolean delete(int accountId, int userId) {
        return accountDao.delete(accountId, userId);
    }
}