package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/postgres");
        config.setUsername("postgres");
        config.setPassword("Pattaya2023");

        dataSource = new HikariDataSource(config);
    }

    public UserModel findByEmailAndHash(String email, String hash) {
        UserModel userModel = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from users where email = ? and password = ?");
            ps.setString(1, email);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userModel = new UserModel();
                userModel.setEmail(rs.getString("email"));
                userModel.setId(rs.getInt("id"));
                userModel.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return userModel;
    }

    public UserModel insert(String email, String hash) {


        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into users (email, password) values (?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, email);
            ps.setString(2, hash);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(generatedKeys.getInt(1));
                userModel.setEmail(email);
                userModel.setPassword(hash);

                return userModel;
            } else {
                throw new CustomException("Can`t generate !");
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }
}