package controller;

import dao.UserDAO;
import util.PasswordResetTokenStore;
import util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResetPasswordServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String contextPath =
                request.getContextPath();

        String token =
                request.getParameter("token");

        if (token == null
                || token.trim().isEmpty()
                || !PasswordResetTokenStore.isValid(token)) {

            response.sendRedirect(
                    contextPath
                            + "/auth/forgot-password.jsp?error=Reset Link Is Invalid Or Expired"
            );

            return;
        }

        request.setAttribute("token", token);

        request.getRequestDispatcher(
                "/auth/reset-password.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String contextPath =
                request.getContextPath();

        String token =
                request.getParameter("token");

        String newPassword =
                request.getParameter("newPassword");

        String confirmPassword =
                request.getParameter("confirmPassword");

        if (token == null
                || token.trim().isEmpty()
                || !PasswordResetTokenStore.isValid(token)) {

            response.sendRedirect(
                    contextPath
                            + "/auth/forgot-password.jsp?error=Reset Link Is Invalid Or Expired"
            );

            return;
        }

        if (newPassword == null
                || confirmPassword == null
                || newPassword.trim().isEmpty()
                || confirmPassword.trim().isEmpty()) {

            response.sendRedirect(
                    contextPath
                            + "/resetPassword?token=" + token + "&error=All Fields Are Required"
            );

            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            response.sendRedirect(
                    contextPath
                            + "/resetPassword?token=" + token + "&error=Passwords Do Not Match"
            );

            return;
        }

        if (!PasswordUtil.isStrongPassword(newPassword)) {

            response.sendRedirect(
                    contextPath
                            + "/resetPassword?token=" + token + "&error=Password Must Contain Uppercase Lowercase Number And Special Character"
            );

            return;
        }

        Integer userId =
                PasswordResetTokenStore.getUserId(token);

        if (userId == null) {

            response.sendRedirect(
                    contextPath
                            + "/auth/forgot-password.jsp?error=Reset Link Is Invalid Or Expired"
            );

            return;
        }

        boolean isUpdated =
                userDAO.changePassword(
                        userId,
                        newPassword
                );

        if (isUpdated) {

            PasswordResetTokenStore.consumeToken(token);

            response.sendRedirect(
                    contextPath
                            + "/auth/login.jsp?success=Password Reset Successfully. Please Login"
            );

        } else {

            response.sendRedirect(
                    contextPath
                            + "/resetPassword?token=" + token + "&error=Failed To Reset Password"
            );
        }
    }
}
