package ru.shapovalov.dao;

import org.springframework.stereotype.Service;
import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDao {
    private final DataSource dataSource;

    public AccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<AccountModel> getAllByUserId(int userId) {
        List<AccountModel> accountModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AccountModel accountModel = new AccountModel();
                accountModel.setUserId(rs.getInt("user_id"));
                accountModel.setName(rs.getString("account_name"));
                accountModel.setBalance(rs.getInt("balance"));
                accountModel.setId(rs.getInt("id"));
                accountModels.add(accountModel);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return accountModels;
    }

    public AccountModel insert(String accountName, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO accounts (account_name, user_id, balance) VALUES (?,?,0)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, accountName);
            ps.setInt(2, userId);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                AccountModel accountModel = new AccountModel();
                accountModel.setId(generatedKeys.getInt(1));
                accountModel.setName(accountName);
                accountModel.setBalance(0);
                accountModel.setUserId(userId);

                return accountModel;
            } else {
                throw new CustomException("Can't generate!");
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public boolean delete(int accountId, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM accounts WHERE id = ? AND user_id = ?");
            ps.setInt(1, accountId);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public boolean edit(int accountId, String newName, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET account_name = ? WHERE id = ? AND user_id = ?");
            ps.setString(1, newName);
            ps.setInt(2, accountId);
            ps.setInt(3, userId);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}