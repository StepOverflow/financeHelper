package ru.shapovalov.api.converter;

import org.springframework.stereotype.Service;
import ru.shapovalov.entity.Account;

import ru.shapovalov.service.AccountDto;

@Service
public class AccountToAccountDtoConverter implements Converter<Account, AccountDto> {
    @Override
    public AccountDto convert(Account source) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountName(source.getName());
        accountDto.setId(source.getId());
        accountDto.setBalance(source.getBalance());
        accountDto.setUserId(source.getUser().getId());
        return accountDto;
    }
}