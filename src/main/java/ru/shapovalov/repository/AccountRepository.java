package ru.shapovalov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shapovalov.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long id);

    Account findByIdAndUserId(Long accountId, Long userId);

    Account findAccountById(Long accountId);
}