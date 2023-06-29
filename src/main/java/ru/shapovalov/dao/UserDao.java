package ru.shapovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

@Service
@RequiredArgsConstructor
public class UserDao {
    private final DataSource dataSource;


    public UserModel findByEmailAndHash(String email, String hash) {
        UserModel userModel = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from users where email = ? and password = ?");
            ps.setString(1, email);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userModel = createUserModelByResultSet(rs);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return userModel;
    }

    public UserModel insert(String email, String hash) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into users (email, password) values (?,?)", Statement.RETURN_GENERATED_KEYS);

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

    public UserModel findByUserId(Integer userId) {
        UserModel userModel = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userModel = createUserModelByResultSet(rs);
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return userModel;
    }

    private UserModel createUserModelByResultSet(ResultSet rs) throws SQLException {
        UserModel userModel = new UserModel();
        userModel.setEmail(rs.getString("email"));
        userModel.setId(rs.getInt("id"));
        userModel.setPassword(rs.getString("password"));
        return userModel;
    }
}