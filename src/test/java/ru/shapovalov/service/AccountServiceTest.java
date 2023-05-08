package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.converter.AccountModelToAccountDtoConverter;
import ru.shapovalov.dao.AccountDao;
import ru.shapovalov.dao.AccountModel;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    AccountService subj;

    @Mock
    AccountDao accountDao;

    @Mock
    AccountModelToAccountDtoConverter accountDtoConverter;

    @Test
    public void getAll_success() {
        int userId = 1;

        AccountModel accountModel1 = new AccountModel();
        accountModel1.setId(1);
        accountModel1.setName("Account 1");
        accountModel1.setUserId(userId);

        AccountModel accountModel2 = new AccountModel();
        accountModel2.setId(2);
        accountModel2.setName("Account 2");
        accountModel2.setUserId(userId);

        AccountDto accountDto1 = new AccountDto();
        accountDto1.setId(accountModel1.getId());
        accountDto1.setName(accountModel1.getName());
        accountDto1.setUserId(accountModel1.getUserId());

        AccountDto accountDto2 = new AccountDto();
        accountDto2.setId(accountModel2.getId());
        accountDto2.setName(accountModel2.getName());
        accountDto2.setUserId(accountModel2.getUserId());

        List <AccountModel> accountModels = Arrays.asList(accountModel1, accountModel2);

        List<AccountDto> expectedAccountDtos = Arrays.asList(accountDto1, accountDto2);

        when(accountDao.getAllByUserId(userId)).thenReturn(accountModels);
        when(accountDtoConverter.convert(accountModel1)).thenReturn(accountDto1);
        when(accountDtoConverter.convert(accountModel2)).thenReturn(accountDto2);

        List<AccountDto> actualAccountDtos = subj.getAll(userId);

        assertEquals(expectedAccountDtos, actualAccountDtos);
        verify(accountDao, times(1)).getAllByUserId(userId);
        verify(accountDtoConverter, times(2)).convert(any(AccountModel.class));
    }

    @Test
    public void create_newAccountCreate() {
        int userId = 1;
        String accountName = "newAccount";

        AccountModel accountModel1 = new AccountModel();
        accountModel1.setId(1);
        accountModel1.setName(accountName);
        accountModel1.setUserId(userId);

        AccountDto exceptedAccountDto = new AccountDto();
        exceptedAccountDto.setId(accountModel1.getId());
        exceptedAccountDto.setName(accountModel1.getName());
        exceptedAccountDto.setUserId(accountModel1.getUserId());

        when(accountDao.insert(accountName, userId)).thenReturn(accountModel1);
        when(accountDtoConverter.convert(accountModel1)).thenReturn(exceptedAccountDto);

        AccountDto actualAccountDto = subj.create(accountName, userId);

        assertEquals(exceptedAccountDto, actualAccountDto);

        verify(accountDao, times(1)).insert(accountName, userId);
        verify(accountDtoConverter, times(1)).convert(any(AccountModel.class));
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