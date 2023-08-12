package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.AccountToAccountDtoConverter;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountToAccountDtoConverter accountDtoConverter;

    public List<AccountDto> getAll(Long userId) {
        return accountRepository.findAccountsByUserId(userId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    public AccountDto create(String accountName, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Account account = new Account();
        account.setName(accountName);
        account.setUser(user.get());
        account.setBalance(0);
        account = accountRepository.save(account);
        return accountDtoConverter.convert(account);
    }

    public boolean edit(Long accountId, String newName, Long userId) {
        Account account = accountRepository.findByIdAndUserId(accountId, userId);
        if (account != null) {
            account.setName(newName);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public boolean delete(Long accountId, Long userId) {
        Account account = accountRepository.findByIdAndUserId(accountId, userId);
        if (account != null) {
            accountRepository.delete(account);
            return true;
        }
        return false;
    }

    public List<AccountDto> findAccountsByUserId(Long userId) {
        return accountRepository.findAccountsByUserId(userId).stream()
                .map(accountDtoConverter::convert).collect(toList());
    }

    public AccountDto findById(Long accountId) {
        return accountDtoConverter.convert(accountRepository.findAccountById(accountId));
    }
}