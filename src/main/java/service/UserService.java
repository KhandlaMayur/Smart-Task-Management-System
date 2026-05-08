package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

public class UserService {

    private UserDAO userDAO;

    // =========================================
    // CONSTRUCTOR
    // =========================================

    public UserService() {

        userDAO = new UserDAO();
    }

    // =========================================
    // REGISTER USER
    // =========================================

    public boolean registerUser(User user,
                                String confirmPassword) {

        // =========================================
        // VALIDATION
        // =========================================

        if (user == null) {

            return false;
        }

        if (user.getName() == null
                || user.getName().trim().isEmpty()) {

            return false;
        }

        if (user.getEmail() == null
                || user.getEmail().trim().isEmpty()) {

            return false;
        }

        if (user.getPassword() == null
                || user.getPassword().trim().isEmpty()) {

            return false;
        }

        // =========================================
        // PASSWORD MATCH CHECK
        // =========================================

        if (!user.getPassword().equals(confirmPassword)) {

            return false;
        }

        // =========================================
        // PASSWORD LENGTH CHECK
        // =========================================

        if (user.getPassword().length() < 6) {

            return false;
        }

        if (!PasswordUtil.isStrongPassword(user.getPassword())) {

            return false;
        }

        // =========================================
        // EMAIL EXISTS CHECK
        // =========================================

        boolean emailExists =
                userDAO.isEmailExists(
                        user.getEmail()
                );

        if (emailExists) {

            return false;
        }

        // =========================================
        // HASH PASSWORD
        // =========================================

        user.setEmail(
                user.getEmail().trim().toLowerCase()
        );

        String hashedPassword =
                PasswordUtil.hashPassword(
                        user.getPassword()
                );

        user.setPassword(hashedPassword);

        // DEFAULT PROFILE IMAGE

        if (user.getProfileImage() == null
                || user.getProfileImage().trim().isEmpty()) {

            user.setProfileImage("default.png");
        }

        // =========================================
        // SAVE USER
        // =========================================

        return userDAO.registerUser(user);
    }

    // =========================================
    // LOGIN USER
    // =========================================

    public User loginUser(String email,
                          String password) {

        // =========================================
        // VALIDATION
        // =========================================

        if (email == null
                || email.trim().isEmpty()
                || password == null
                || password.trim().isEmpty()) {

            return null;
        }

        return userDAO.loginUser(
                email.trim().toLowerCase(),
                password
        );
    }

    // =========================================
    // UPDATE PROFILE
    // =========================================

    public boolean updateProfile(User user) {

        // =========================================
        // VALIDATION
        // =========================================

        if (user == null) {

            return false;
        }

        if (user.getName() == null
                || user.getName().trim().isEmpty()) {

            return false;
        }

        if (user.getEmail() == null
                || user.getEmail().trim().isEmpty()) {

            return false;
        }

        user.setEmail(
                user.getEmail().trim().toLowerCase()
        );

        return userDAO.updateProfile(user);
    }

    // =========================================
    // CHANGE PASSWORD
    // =========================================

    public boolean changePassword(int userId,
                                  String currentPassword,
                                  String newPassword,
                                  String confirmPassword) {

        // =========================================
        // VALIDATION
        // =========================================

        if (currentPassword == null
                || currentPassword.trim().isEmpty()
                || newPassword == null
                || newPassword.trim().isEmpty()
                || confirmPassword == null
                || confirmPassword.trim().isEmpty()) {

            return false;
        }

        // =========================================
        // PASSWORD MATCH CHECK
        // =========================================

        if (!newPassword.equals(confirmPassword)) {

            return false;
        }

        // =========================================
        // PASSWORD LENGTH CHECK
        // =========================================

        if (newPassword.length() < 6) {

            return false;
        }

        // =========================================
        // VERIFY CURRENT PASSWORD
        // =========================================

        boolean validPassword =
                userDAO.verifyCurrentPassword(
                        userId,
                        currentPassword
                );

        if (!validPassword) {

            return false;
        }

        // =========================================
        // UPDATE PASSWORD
        // =========================================

        return userDAO.changePassword(
                userId,
                newPassword
        );
    }

    // =========================================
    // GET USER BY ID
    // =========================================

    public User getUserById(int id) {

        return userDAO.getUserById(id);
    }

    // =========================================
    // CHECK EMAIL EXISTS
    // =========================================

    public boolean isEmailExists(String email) {

        if (email == null) {

            return false;
        }

        return userDAO.isEmailExists(
                email.trim().toLowerCase()
        );
    }

    // =========================================
    // GET TOTAL USERS
    // =========================================

    public int getTotalUsers() {

        return userDAO.getTotalUsers();
    }
}
