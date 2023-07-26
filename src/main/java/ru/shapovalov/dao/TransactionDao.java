package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionDao {
    private final DataSource dataSource;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public Transaction moneyTransfer(Integer fromAccount, Integer toAccount, int amountPaid, int userId, List<Integer> categoryIds) {
        try {
            Account from = null;
            Account to = null;
            if (fromAccount != null) {
                from = entityManager.find(Account.class, fromAccount);
                if (from.getUser().getId() != userId) {
                    throw new CustomException("You don't have permission to perform this operation on this account.");
                }
                if (from.getBalance() < amountPaid) {
                    throw new CustomException("Insufficient funds on the account");
                }

            }
            if (toAccount != null) {
                to = entityManager.find(Account.class, toAccount);
            }

            Transaction transaction = new Transaction();
            transaction.setFromAccount(from);
            transaction.setToAccount(to);
            transaction.setAmountPaid(amountPaid);
            transaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            entityManager.persist(transaction);

            if (categoryIds != null && !categoryIds.isEmpty()) {
                List<Category> categories = new ArrayList<>();
                for (Integer categoryId : categoryIds) {
                    if (categoryId != null) {
                        Category category = entityManager.find(Category.class, categoryId);
                        if (category != null) {
                            categories.add(category);
                        }
                    }
                }
                transaction.setCategories(categories);
            }

            if (from != null) {
                from.setBalance(from.getBalance() - amountPaid);
            }
            if (to != null) {
                to.setBalance(to.getBalance() + amountPaid);
            }

            return transaction;
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}