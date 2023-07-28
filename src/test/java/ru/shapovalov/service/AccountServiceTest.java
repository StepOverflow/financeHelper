package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.converter.AccountToAccountDtoConverter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    AccountService subj;

    @Mock
    AccountDao accountDao;

    @Mock
    AccountToAccountDtoConverter accountDtoConverter;

    @Test
    public void getAll_success() {
        User user = new User();
        user.setId(1);

        Account accountModel1 = new Account();
        accountModel1.setId(1);
        accountModel1.setName("Account 1");
        accountModel1.setUser(user);

        Account accountModel2 = new Account();
        accountModel2.setId(2);
        accountModel2.setName("Account 2");
        accountModel2.setUser(user);

        AccountDto accountDto1 = new AccountDto();
        accountDto1.setId(accountModel1.getId());
        accountDto1.setAccountName(accountModel1.getName());
        accountDto1.setUserId(accountModel1.getUser().getId());

        AccountDto accountDto2 = new AccountDto();
        accountDto2.setId(accountModel2.getId());
        accountDto2.setAccountName(accountModel2.getName());
        accountDto2.setUserId(accountModel2.getUser().getId());

        List<Account> accountModels = Arrays.asList(accountModel1, accountModel2);

        List<AccountDto> expectedAccountDtos = Arrays.asList(accountDto1, accountDto2);

        when(accountDao.getAllByUserId(user.getId())).thenReturn(accountModels);
        when(accountDtoConverter.convert(accountModel1)).thenReturn(accountDto1);
        when(accountDtoConverter.convert(accountModel2)).thenReturn(accountDto2);

        List<AccountDto> actualAccountDtos = subj.getAll(user.getId());

        assertEquals(expectedAccountDtos, actualAccountDtos);
        verify(accountDao, times(1)).getAllByUserId(user.getId());
        verify(accountDtoConverter, times(2)).convert(any(Account.class));
    }

    @Test
    public void create_newAccountCreate() {
        User user = new User();
        user.setId(1);
        String accountName = "newAccount";

        Account accountModel1 = new Account();
        accountModel1.setId(1);
        accountModel1.setName(accountName);
        accountModel1.setUser(user);

        AccountDto exceptedAccountDto = new AccountDto();
        exceptedAccountDto.setId(accountModel1.getId());
        exceptedAccountDto.setAccountName(accountModel1.getName());
        exceptedAccountDto.setUserId(accountModel1.getUser().getId());

        when(accountDao.insert(accountName, user.getId())).thenReturn(accountModel1);
        when(accountDtoConverter.convert(accountModel1)).thenReturn(exceptedAccountDto);

        AccountDto actualAccountDto = subj.create(accountName, user.getId());

        assertEquals(exceptedAccountDto, actualAccountDto);

        verify(accountDao, times(1)).insert(accountName, user.getId());
        verify(accountDtoConverter, times(1)).convert(any(Account.class));
    }

    @Test
    public void delete_accountDelete() {
        int accountId = 2;
        int userId = 1;

        when(accountDao.delete(accountId, userId)).thenReturn(true);

        assertTrue(subj.delete(accountId, userId));

        verify(accountDao, times(1)).delete(accountId, userId);
    }

    @Test
    public void delete_accountNotDelete() {
        int accountId = 2;
        int userId = 1;

        when(accountDao.delete(accountId, userId)).thenReturn(false);

        assertFalse(subj.delete(accountId, userId));

        verify(accountDao, times(1)).delete(accountId, userId);
    }
}