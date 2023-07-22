package ru.shapovalov.dao;

import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static java.util.Collections.*;

@Repository
@Transactional
public class AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> getAllByUserId(int userId) {
        try {
            return entityManager.createNamedQuery("Account.getAllByUserId", Account.class)
                    .setParameter("user_id", userId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return emptyList();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return false;
    }

    public boolean edit(int accountId, String newName, int userId) {
        try {
            Account account = entityManager.find(Account.class, accountId);
            if (account != null && account.getUser().getId() == userId) {
                account.setName(newName);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}