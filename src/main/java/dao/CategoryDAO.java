package dao;

import model.Category;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // =========================================
    // ADD CATEGORY
    // =========================================

    public boolean addCategory(Category category) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO categories(category_name) VALUES(?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    category.getCategoryName()
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // UPDATE CATEGORY
    // =========================================

    public boolean updateCategory(Category category) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE categories SET category_name = ? WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    category.getCategoryName()
            );

            ps.setInt(
                    2,
                    category.getId()
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // DELETE CATEGORY
    // =========================================

    public boolean deleteCategory(int id) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM categories WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // GET CATEGORY BY ID
    // =========================================

    public Category getCategoryById(int id) {

        Category category = null;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM categories WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                category = new Category();

                category.setId(
                        rs.getInt("id")
                );

                category.setCategoryName(
                        rs.getString("category_name")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return category;
    }

    // =========================================
    // GET ALL CATEGORIES
    // =========================================

    public List<Category> getAllCategories() {

        List<Category> categoryList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM categories ORDER BY category_name ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Category category =
                        new Category();

                category.setId(
                        rs.getInt("id")
                );

                category.setCategoryName(
                        rs.getString("category_name")
                );

                categoryList.add(category);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return categoryList;
    }

    // =========================================
    // CHECK CATEGORY EXISTS
    // =========================================

    public boolean isCategoryExists(String categoryName) {

        boolean exists = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM categories WHERE category_name = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, categoryName);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                exists = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return exists;
    }

    // =========================================
    // GET TOTAL CATEGORY COUNT
    // =========================================

    public int getTotalCategories() {

        int count = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM categories";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                count = rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return count;
    }
}