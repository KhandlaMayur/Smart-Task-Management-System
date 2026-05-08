package controller;

import dao.UserDAO;
import model.User;
import util.EmailUtil;
import util.PasswordResetTokenStore;
import util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ForgotPasswordServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("auth/forgot-password.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email != null) {

            email = email.trim().toLowerCase();
        }

        if (!ValidationUtil.isValidEmail(email)) {

            response.sendRedirect(
                    "auth/forgot-password.jsp?error=Enter A Valid Registered Email Address"
            );

            return;
        }

        User user =
                userDAO.getUserByEmail(email);

        if (user == null) {

            response.sendRedirect(
                    "auth/forgot-password.jsp?error=Email Not Registered"
            );

            return;
        }

        String token =
                PasswordResetTokenStore.createToken(user);

        String resetUrl =
                buildResetUrl(request, token);

        HttpSession session =
                request.getSession();

        session.removeAttribute("resetLinkPreview");

        boolean emailSent =
                EmailUtil.sendPasswordResetEmail(
                        user.getEmail(),
                        user.getName(),
                        resetUrl
                );

        if (emailSent) {

            response.sendRedirect(
                    "auth/forgot-password.jsp?success=Reset Link Sent To Your Email"
            );

            return;
        }

        session.setAttribute(
                "resetLinkPreview",
                resetUrl
        );

        response.sendRedirect(
                "auth/forgot-password.jsp?warning=Email Service Not Configured. Use The Reset Link Below"
        );
    }

    private String buildResetUrl(HttpServletRequest request,
                                 String token) {

        String scheme =
                request.getScheme();

        String serverName =
                request.getServerName();

        int serverPort =
                request.getServerPort();

        String contextPath =
                request.getContextPath();

        StringBuilder baseUrl =
                new StringBuilder();

        baseUrl.append(scheme)
                .append("://")
                .append(serverName);

        boolean isStandardHttpPort =
                "http".equalsIgnoreCase(scheme)
                        && serverPort == 80;

        boolean isStandardHttpsPort =
                "https".equalsIgnoreCase(scheme)
                        && serverPort == 443;

        if (!isStandardHttpPort
                && !isStandardHttpsPort) {

            baseUrl.append(':')
                    .append(serverPort);
        }

        baseUrl.append(contextPath)
                .append("/resetPassword?token=")
                .append(token);

        return baseUrl.toString();
    }
}
