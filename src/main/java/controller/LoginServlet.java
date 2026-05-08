package controller;

import dao.UserDAO;
import model.User;
import util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

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

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null) {

            email = email.trim().toLowerCase();
        }

        // =========================================
        // VALIDATION
        // =========================================

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            response.sendRedirect(
                    "auth/login.jsp?error=Email And Password Required"
            );

            return;
        }

        // =========================================
        // LOGIN VALIDATION
        // =========================================

        User user =
                userDAO.getUserByEmail(email);

        if (user != null) {

            if (!userDAO.isPasswordValid(user, password)) {

                response.sendRedirect(
                        "auth/login.jsp?error=Invalid Password"
                );

                return;
            }

            // =========================================
            // CREATE SESSION
            // =========================================

            SessionUtil.createUserSession(
                    request,
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getProfileImage()
            );

            // =========================================
            // REDIRECT DASHBOARD
            // =========================================

            response.sendRedirect(
                    "dashboard"
            );

        } else {

            // =========================================
            // LOGIN FAILED
            // =========================================

            response.sendRedirect(
                    "auth/login.jsp?error=Email Not Registered. Please Register First"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("auth/login.jsp");
    }
}
