package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.AccountToAccountDtoConverter;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.UserRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountToAccountDtoConverter accountDtoConverter;

    public List<AccountDto> getAll(Long userId) {
        return accountRepository.findAllByUserId(userId).stream()
                .map(accountDtoConverter::convert)
                .collect(toList());
    }

    public AccountDto create(String accountName, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("user not found"));
        Account account = new Account();
        account.setName(accountName);
        account.setUser(user);
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

    public List<AccountDto> findAllByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId).stream()
                .map(accountDtoConverter::convert).collect(toList());
    }
}