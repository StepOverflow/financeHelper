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

    public List<AccountDTO> getAllAccountsByUserId(int userId) {
        List<AccountDTO> accountDTOs = new ArrayList<>();
        List<AccountModel> accountModels = accountDao.getAllByUserId(userId);
        for (AccountModel accountModel : accountModels) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountName(accountModel.getAccountName());
            accountDTO.setBalance(accountModel.getBalance());
            accountDTO.setId(accountModel.getId());
            accountDTOs.add(accountDTO);
        }
        return accountDTOs;
    }

    public AccountDTO createAccount(String accountName, int userId) {
        AccountModel accountModel = accountDao.insert(accountName, userId);
        if (accountModel == null) {
            return null;
        }
        return accountDtoConverter.convert(accountModel);
    }

    public boolean deleteAccount(int accountId, int id) {
        return accountDao.delete(accountId, id);
    }
}


