package converter;

import dao.AccountModel;
import service.AccountDTO;

public class AccountModelToAccountDtoConverter<S, T> implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountName(source.getAccountName());
        accountDTO.setId(source.getId());
        accountDTO.setBalance(source.getBalance());
        accountDTO.setUserId(source.getUserId());
        return accountDTO;
    }
}
