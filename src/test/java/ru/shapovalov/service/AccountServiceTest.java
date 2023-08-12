package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.shapovalov.api.converter.AccountToAccountDtoConverter;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountToAccountDtoConverter accountDtoConverter;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testGetAllAccounts() {
        Long userId = 1L;
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());

        when(accountRepository.findAccountsByUserId(userId)).thenReturn(accounts);
        when(accountDtoConverter.convert(any(Account.class))).thenReturn(new AccountDto());

        List<AccountDto> accountDtos = accountService.getAll(userId);

        assertEquals(accounts.size(), accountDtos.size());
        verify(accountRepository, times(1)).findAccountsByUserId(userId);
        verify(accountDtoConverter, times(accounts.size())).convert(any(Account.class));
    }

    @Test
    public void testCreateAccount() {
        Long userId = 1L;
        String accountName = "New Account";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());
        when(accountDtoConverter.convert(any(Account.class))).thenReturn(new AccountDto());

        AccountDto accountDto = accountService.create(accountName, userId);

        assertNotNull(accountDto);
        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(accountDtoConverter, times(1)).convert(any(Account.class));
    }

    @Test
    public void testEditAccount() {
        Long accountId = 1L;
        Long userId = 1L;
        String newName = "Updated Name";
        Account account = new Account();
        account.setUser(new User());
        account.getUser().setId(userId);

        when(accountRepository.findByIdAndUserId(accountId, userId)).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        boolean result = accountService.edit(accountId, newName, userId);

        assertTrue(result);
        assertEquals(newName, account.getName());
        verify(accountRepository, times(1)).findByIdAndUserId(accountId, userId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testDeleteAccount() {
        Long accountId = 1L;
        Long userId = 1L;
        Account account = new Account();
        account.setUser(new User());
        account.getUser().setId(userId);

        when(accountRepository.findByIdAndUserId(accountId, userId)).thenReturn(account);

        boolean result = accountService.delete(accountId, userId);

        assertTrue(result);
        verify(accountRepository, times(1)).findByIdAndUserId(accountId, userId);
        verify(accountRepository, times(1)).delete(account);
    }
}

