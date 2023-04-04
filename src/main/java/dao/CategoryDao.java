package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

public class CategoryDao {
    private final DataSource dataSource;

    public CategoryDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5433/postgres");
        config.setUsername("postgres");
        config.setPassword("Pattaya2023");

        dataSource = new HikariDataSource(config);
    }

    public CategoryModel insert(String categoryName, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into categories (name, user_id) values (?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, categoryName);
            ps.setInt(2, userId);

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(generatedKeys.getInt(1));
                categoryModel.setName(categoryName);
                categoryModel.setUserId(userId);

                return categoryModel;
            } else {
                throw new CustomException("Can`t generate !");
            }

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public boolean delete(int categoryId, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM categories WHERE id = ?");
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    if (id != userId) {
                        return false;
                    }
                } else {
                    return false;
                }
            }

            ps = conn.prepareStatement("DELETE FROM categories WHERE id = ?");
            ps.setInt(1, categoryId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public CategoryModel edit(int categoryId, String newCategoryName, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM categories WHERE id = ?");
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    if (id != userId) {
                        return null;
                    }
                } else {
                    return null;
                }
                ps = conn.prepareStatement("update categories set name = ? where id = ?");
                ps.setString(1, newCategoryName);
                ps.setInt(2, categoryId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new CustomException(e);
            }
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(categoryId);
                categoryModel.setName(newCategoryName);
                categoryModel.setUserId(userId);

                return categoryModel;

            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return null;
    }
}