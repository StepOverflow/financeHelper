package converter;

import dao.AccountModel;
import service.AccountDto;

public class AccountModelToAccountDtoConverter implements Converter<AccountModel, AccountDto> {
    @Override
    public AccountDto convert(AccountModel source) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountName(source.getAccountName());
        accountDto.setId(source.getId());
        accountDto.setBalance(source.getBalance());
        accountDto.setUserId(source.getUserId());
        return accountDto;
    }
}
