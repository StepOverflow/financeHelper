package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.AccountToAccountDtoConverter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.entity.Account;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountDao accountDao;
    private final AccountToAccountDtoConverter accountDtoConverter;

    public List<AccountDto> getAll(Long userId) {
        return accountDao.getAllByUserId(userId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    public AccountDto create(String accountName, Long userId) {
        Account account = accountDao.insert(accountName, userId);
        return accountDtoConverter.convert(account);
    }

    public boolean edit(Long accountId, String newName, Long userId) {
        return accountDao.edit(accountId, newName, userId);
    }

    public boolean delete(Long accountId, Long userId) {
        return accountDao.delete(accountId, userId);
    }
}