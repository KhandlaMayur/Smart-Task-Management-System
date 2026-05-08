package controller;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // =========================================
        // GET FORM DATA
        // =========================================

        String name = request.getParameter("name");

        String email = request.getParameter("email");

        String password = request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");

        if (name != null) {

            name = name.trim();
        }

        if (email != null) {

            email = email.trim().toLowerCase();
        }

        // =========================================
        // VALIDATION
        // =========================================

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {

            response.sendRedirect(
                    "auth/register.jsp?error=All Fields Are Required"
            );

            return;
        }

        // =========================================
        // PASSWORD MATCH VALIDATION
        // =========================================

        if (!password.equals(confirmPassword)) {

            response.sendRedirect(
                    "auth/register.jsp?error=Passwords Do Not Match"
            );

            return;
        }

        // =========================================
        // PASSWORD LENGTH VALIDATION
        // =========================================

        if (password.length() < 6) {

            response.sendRedirect(
                    "auth/register.jsp?error=Password Must Be At Least 6 Characters"
            );

            return;
        }

        if (!PasswordUtil.isStrongPassword(password)) {

            response.sendRedirect(
                    "auth/register.jsp?error=Password Must Contain Uppercase, Lowercase, Number And Special Character"
            );

            return;
        }

        // =========================================
        // CHECK EMAIL EXIST
        // =========================================

        boolean emailExists =
                userDAO.isEmailExists(email);

        if (emailExists) {

            response.sendRedirect(
                    "auth/register.jsp?error=Email Already Registered"
            );

            return;
        }

        // =========================================
        // HASH PASSWORD
        // =========================================

        String hashedPassword =
                PasswordUtil.hashPassword(password);

        // =========================================
        // CREATE USER OBJECT
        // =========================================

        User user = new User();

        user.setName(name);

        user.setEmail(email);

        user.setPassword(hashedPassword);

        // DEFAULT PROFILE IMAGE

        user.setProfileImage("default.png");

        // =========================================
        // REGISTER USER
        // =========================================

        boolean isRegistered =
                userDAO.registerUser(user);

        if (isRegistered) {

            response.sendRedirect(
                    "auth/login.jsp?success=Registration Successful"
            );

        } else {

            response.sendRedirect(
                    "auth/register.jsp?error=Registration Failed"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("auth/register.jsp");
    }
}
