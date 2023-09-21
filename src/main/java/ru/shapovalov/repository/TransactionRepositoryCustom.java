package ru.shapovalov.repository;

import ru.shapovalov.entity.Transaction;

import java.util.List;

public interface TransactionRepositoryCustom {
    List<Transaction> findByFilter(TransactionFilter filter);
}