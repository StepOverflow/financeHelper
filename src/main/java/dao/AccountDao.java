package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final DataSource dataSource;

    public AccountDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/postgres");
        config.setUsername("postgres");
        config.setPassword("Pattaya2023");

        dataSource = new HikariDataSource(config);
    }

    public List<AccountModel> getAllByUserId(int userId) {
        List<AccountModel> accountModels = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from accounts where user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AccountModel accountModel = new AccountModel();
                accountModel.setUserId(rs.getInt("user_id"));
                accountModel.setAccountName(rs.getString("account_name"));
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
            PreparedStatement ps = conn.prepareStatement("insert into accounts (account_name, user_id, balance) values (?,?,0)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, accountName);
            ps.setInt(2, userId);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                AccountModel accountModel = new AccountModel();
                accountModel.setId(generatedKeys.getInt(1));
                accountModel.setAccountName(accountName);
                accountModel.setBalance(0);
                accountModel.setUserId(userId);

                return accountModel;
            } else {
                throw new CustomException("Can`t generate !");
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public boolean delete(int accountId, int id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM accounts WHERE id = ?");
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    if (userId != id) {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            ps = conn.prepareStatement("DELETE FROM accounts WHERE id = ?");
            ps.setInt(1, accountId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

}
