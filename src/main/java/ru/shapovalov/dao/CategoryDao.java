package ru.shapovalov.dao;

import ru.shapovalov.exception.CustomException;

import javax.sql.DataSource;
import java.sql.*;

public class CategoryDao {
    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CategoryModel insert(String categoryName, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into categories (name, user_id) values (?,?)", Statement.RETURN_GENERATED_KEYS);

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

    public boolean delete(int id, int userId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM categories WHERE id = ? and user_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new CustomException(e);
        }
    }

    public CategoryModel edit(int id, String newCategoryName, int userId) {
        CategoryModel categoryModel = new CategoryModel();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("update categories set name = ? where id = ? and user_id = ?", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCategoryName);
            ps.setInt(2, id);
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