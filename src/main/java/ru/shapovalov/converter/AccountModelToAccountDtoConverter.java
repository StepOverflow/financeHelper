package ru.shapovalov.converter;

import ru.shapovalov.dao.AccountModel;
import ru.shapovalov.service.AccountDto;

public class AccountModelToAccountDtoConverter implements Converter<AccountModel, AccountDto> {
    @Override
    public AccountDto convert(AccountModel source) {
        AccountDto accountDto = new AccountDto();
        accountDto.setName(source.getName());
        accountDto.setId(source.getId());
        accountDto.setBalance(source.getBalance());
        accountDto.setUserId(source.getUserId());
        return accountDto;
    }
}