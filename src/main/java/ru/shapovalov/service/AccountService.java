package ru.shapovalov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shapovalov.converter.Converter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.dao.AccountModel;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Slf4j
@Service
public class AccountService {
    private final AccountDao accountDao;
    private final Converter<AccountModel, AccountDto> accountDtoConverter;


    public AccountService(AccountDao accountDao, Converter<AccountModel, AccountDto> accountDtoConverter) {
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
        log.info("start create account");
        return accountDtoConverter.convert(accountModel);
    }

    public boolean delete(int accountId, int userId) {
        return accountDao.delete(accountId, userId);
    }
}