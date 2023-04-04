package service;

import converter.AccountModelToAccountDtoConverter;
import dao.AccountDao;
import dao.AccountModel;
import dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final AccountModelToAccountDtoConverter accountDtoConverter;

    public AccountService() {
        this.userDao = new UserDao();
        this.accountDao = new AccountDao();
        this.accountDtoConverter = new AccountModelToAccountDtoConverter();
    }

    public List<AccountDto> getAllAccountsByUserId(int userId) {
        List<AccountDto> accountDtos = new ArrayList<>();
        List<AccountModel> accountModels = accountDao.getAllByUserId(userId);
        for (AccountModel accountModel : accountModels) {
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountName(accountModel.getAccountName());
            accountDto.setBalance(accountModel.getBalance());
            accountDto.setId(accountModel.getId());
            accountDtos.add(accountDto);
        }
        return accountDtos;
    }

    public AccountDto createAccount(String accountName, int userId) {
        AccountModel accountModel = accountDao.insert(accountName, userId);
        if (accountModel == null) {
            return null;
        }
        return accountDtoConverter.convert(accountModel);
    }

    public boolean deleteAccount(int accountId, int userId) {
        return accountDao.delete(accountId, userId);
    }
}


