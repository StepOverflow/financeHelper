package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class AccountDao {
    private final DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> getAllByUserId(int userId) {
        try {
            return entityManager.createNamedQuery("Account.getAllByUserId", Account.class)
                    .setParameter("user_id", userId)
                    .getResultList();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public Account insert(String accountName, int userId) {
        try {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                Account account = new Account();
                account.setName(accountName);
                account.setBalance(0);
                account.setUser(user);
                entityManager.persist(account);
                return account;
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }

        return null;
    }

    public boolean delete(int accountId, int userId) {
        try {
            Account account = entityManager.find(Account.class, accountId);
            if (account != null && account.getUser().getId() == userId) {
                entityManager.remove(account);
                return true;
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }

        return false;
    }

    public boolean edit(int accountId, String newName, int userId) {
        try {
            Account account = entityManager.find(Account.class, accountId);
            if (account != null && account.getUser().getId() == userId) {
                account.setName(newName);
                entityManager.persist(account);
                return true;
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return false;
    }
}