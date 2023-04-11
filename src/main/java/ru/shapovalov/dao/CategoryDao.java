package ru.shapovalov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.shapovalov.exception.CustomException;

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

    public boolean delete(String name, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM categories WHERE name = ? and user_id = ?");
            ps.setString(1, name);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public CategoryModel edit(String name, String newCategoryName, int userId) {
        CategoryModel categoryModel = new CategoryModel();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "update categories set name = ? where name = ? and user_id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCategoryName);
            ps.setString(2, name);
            ps.setInt(3, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                categoryModel.setName(newCategoryName);
                categoryModel.setUserId(userId);
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    categoryModel.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new CustomException(e);
        }
        return categoryModel;
    }
}