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

    public List<Account> getAllByUserId(Long userId) {
        try {
            return entityManager.createQuery("SELECT a FROM Account AS a WHERE a.user.id = :user_id", Account.class)
                    .setParameter("user_id", userId)
                    .getResultList();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public Account insert(String accountName, Long userId) {
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

    public boolean delete(Long accountId, Long userId) {
        try {
            Account account = entityManager.find(Account.class, accountId);
            if (account != null && account.getUser().getId().equals(userId)) {
                entityManager.remove(account);
                return true;
            }
        } catch (Exception e) {
            throw new CustomException(e);
        }

        return false;
    }

    public boolean edit(Long accountId, String newName, Long userId) {
        try {
            Account account = entityManager.find(Account.class, accountId);
            if (account != null && account.getUser().getId().equals(userId)) {
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