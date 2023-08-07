package ru.shapovalov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shapovalov.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccountIdInOrToAccountIdIn(List<Long> accountIds, List<Long> accountIds1);

    List<Transaction> findByToAccountIdIn(List<Long> accountIds);

    List<Transaction> findByFromAccountIdIn(List<Long> accountIds);
}