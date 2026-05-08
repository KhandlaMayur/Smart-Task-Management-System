package dao;

import model.User;
import util.DBConnection;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class UserDAO {

    // =========================================
    // REGISTER USER
    // =========================================

    public boolean registerUser(User user) {

        boolean status = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users(name, email, password, profile_image) VALUES (?, ?, ?, ?)"
             )) {

            ps.setString(1, user.getName());

            ps.setString(2, normalizeEmail(user.getEmail()));

            ps.setString(3, user.getPassword());

            ps.setString(4, user.getProfileImage());

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
    // LOGIN USER
    // =========================================

    public User loginUser(String email,
                          String password) {

        User user =
                getUserByEmail(email);

        if (user != null
                && isPasswordValid(user, password)) {

            return user;
        }

        return null;
    }

    // =========================================
    // CHECK EMAIL EXISTS
    // =========================================

    public boolean isEmailExists(String email) {

        boolean exists = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT 1 FROM users WHERE email = ?"
             )) {

            ps.setString(1, normalizeEmail(email));

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
    // GET USER BY EMAIL
    // =========================================

    public User getUserByEmail(String email) {

        User user = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM users WHERE email = ?"
             )) {

            ps.setString(1, normalizeEmail(email));

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                user = extractUserFromResultSet(rs);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return user;
    }

    // =========================================
    // VERIFY LOGIN PASSWORD
    // =========================================

    public boolean isPasswordValid(User user,
                                   String plainPassword) {

        if (user == null
                || plainPassword == null
                || plainPassword.trim().isEmpty()) {

            return false;
        }

        String storedPassword =
                user.getPassword();

        if (storedPassword == null
                || storedPassword.trim().isEmpty()) {

            return false;
        }

        try {

            if (storedPassword.startsWith("$2a$")
                    || storedPassword.startsWith("$2b$")
                    || storedPassword.startsWith("$2y$")) {

                return BCrypt.checkpw(
                        plainPassword,
                        storedPassword
                );
            }

            boolean plainTextMatch =
                    storedPassword.equals(plainPassword);

            if (plainTextMatch) {

                changePassword(
                        user.getId(),
                        plainPassword
                );
            }

            return plainTextMatch;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================================
    // GET USER BY ID
    // =========================================

    public User getUserById(int id) {

        User user = null;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM users WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                user = extractUserFromResultSet(rs);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return user;
    }

    // =========================================
    // UPDATE PROFILE
    // =========================================

    public boolean updateProfile(User user) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE users SET name = ?, email = ?, profile_image = ? WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    user.getName()
            );

            ps.setString(
                    2,
                    normalizeEmail(user.getEmail())
            );

            ps.setString(
                    3,
                    user.getProfileImage()
            );

            ps.setInt(
                    4,
                    user.getId()
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
    // CHANGE PASSWORD
    // =========================================

    public boolean changePassword(int userId,
                                  String newPassword) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            // HASH PASSWORD

            String hashedPassword =
                    BCrypt.hashpw(
                            newPassword,
                            BCrypt.gensalt()
                    );

            String sql =
                    "UPDATE users SET password = ? WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    hashedPassword
            );

            ps.setInt(
                    2,
                    userId
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
    // VERIFY CURRENT PASSWORD
    // =========================================

    public boolean verifyCurrentPassword(int userId,
                                         String currentPassword) {

        boolean valid = false;

        try {

            User user =
                    getUserById(userId);

            valid =
                    isPasswordValid(
                            user,
                            currentPassword
                    );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return valid;
    }

    // =========================================
    // GET PROFILE IMAGE
    // =========================================

    public String getProfileImage(int userId) {

        String image = "default.png";

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT profile_image FROM users WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                image =
                        rs.getString("profile_image");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return image;
    }

    // =========================================
    // GET TOTAL USERS
    // =========================================

    public int getTotalUsers() {

        int count = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM users";

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

    private String normalizeEmail(String email) {

        if (email == null) {

            return null;
        }

        return email.trim().toLowerCase();
    }

    // =========================================
    // EXTRACT USER OBJECT
    // =========================================

    private User extractUserFromResultSet(ResultSet rs)
            throws Exception {

        User user =
                new User();

        user.setId(
                rs.getInt("id")
        );

        user.setName(
                rs.getString("name")
        );

        user.setEmail(
                rs.getString("email")
        );

        user.setPassword(
                rs.getString("password")
        );

        user.setProfileImage(
                rs.getString("profile_image")
        );

        Timestamp createdAt =
                rs.getTimestamp("created_at");

        if (createdAt != null) {

            user.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        return user;
    }
}
