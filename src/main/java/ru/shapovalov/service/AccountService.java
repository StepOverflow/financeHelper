package ru.shapovalov.service;

import ru.shapovalov.converter.AccountModelToAccountDtoConverter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.dao.AccountModel;
import ru.shapovalov.dao.UserDao;

import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private final AccountDao accountDao;
    private final AccountModelToAccountDtoConverter accountDtoConverter;

    public AccountService() {
        this.accountDao = new AccountDao();
        this.accountDtoConverter = new AccountModelToAccountDtoConverter();
    }

    public List<AccountDto> getAll(int userId) {
        List<AccountModel> accountModels = accountDao.getAllByUserId(userId);
        return accountModels.stream()
                .map(accountDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public AccountDto create(String accountName, int userId) {
        AccountModel accountModel = accountDao.insert(accountName, userId);
        return accountDtoConverter.convert(accountModel);
    }

    public boolean delete(int accountId, int userId) {
        return accountDao.delete(accountId, userId);
    }
}


