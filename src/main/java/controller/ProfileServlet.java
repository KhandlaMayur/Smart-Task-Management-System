package controller;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;
import util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class ProfileServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY =
            "uploads/profile-images";

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

        HttpSession session = request.getSession(false);

        // =========================================
        // SESSION VALIDATION
        // =========================================

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    "auth/login.jsp?error=Please Login First"
            );

            return;
        }

        // =========================================
        // GET ACTION
        // =========================================

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            response.sendRedirect(
                    "profile/profile.jsp?error=Invalid Action"
            );

            return;
        }

        switch (action) {

            case "updateProfile":
                updateProfile(request, response, session);
                break;

            case "changePassword":
                changePassword(request, response, session);
                break;

            default:
                response.sendRedirect(
                        "profile/profile.jsp?error=Unknown Action"
                );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    // =========================================
    // UPDATE PROFILE
    // =========================================

    private void updateProfile(HttpServletRequest request,
                               HttpServletResponse response,
                               HttpSession session)
            throws IOException, ServletException {

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString()
        );

        String name = request.getParameter("name");
        String email = request.getParameter("email");

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
                || email == null || email.trim().isEmpty()) {

            response.sendRedirect(
                    "profile/edit-profile.jsp?error=All Fields Required"
            );

            return;
        }

        if (!ValidationUtil.isValidName(name)) {

            response.sendRedirect(
                    "profile/edit-profile.jsp?error=Enter A Valid Name"
            );

            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {

            response.sendRedirect(
                    "profile/edit-profile.jsp?error=Enter A Valid Email Address"
            );

            return;
        }

        // =========================================
        // IMAGE UPLOAD
        // =========================================

        Part filePart = request.getPart("profileImage");

        String fileName = "";

        if (filePart != null && filePart.getSize() > 0) {

            fileName = Paths.get(
                    filePart.getSubmittedFileName()
            ).getFileName().toString();

            String applicationPath =
                    request.getServletContext().getRealPath("");

            String uploadPath =
                    applicationPath
                            + File.separator
                            + UPLOAD_DIRECTORY;

            File uploadDir = new File(uploadPath);

            // CREATE DIRECTORY IF NOT EXIST

            if (!uploadDir.exists()) {

                uploadDir.mkdirs();
            }

            // SAVE FILE

            filePart.write(
                    uploadPath
                            + File.separator
                            + fileName
            );
        }

        // =========================================
        // CREATE USER OBJECT
        // =========================================

        User user = new User();

        user.setId(userId);
        user.setName(name);
        user.setEmail(email);

        // IF IMAGE NOT UPLOADED
        // KEEP OLD IMAGE

        if (fileName == null || fileName.trim().isEmpty()) {

            fileName = userDAO.getProfileImage(userId);
        }

        user.setProfileImage(fileName);

        // =========================================
        // UPDATE PROFILE
        // =========================================

        boolean isUpdated =
                userDAO.updateProfile(user);

        if (isUpdated) {

            // UPDATE SESSION

            session.setAttribute("userName", name);

            session.setAttribute("userEmail", email);

            session.setAttribute("profileImage", fileName);

            response.sendRedirect(
                    "profile/profile.jsp?success=Profile Updated Successfully"
            );

        } else {

            response.sendRedirect(
                    "profile/edit-profile.jsp?error=Failed To Update Profile"
            );
        }
    }

    // =========================================
    // CHANGE PASSWORD
    // =========================================

    private void changePassword(HttpServletRequest request,
                                HttpServletResponse response,
                                HttpSession session)
            throws IOException {

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString()
        );

        String currentPassword =
                request.getParameter("currentPassword");

        String newPassword =
                request.getParameter("newPassword");

        String confirmPassword =
                request.getParameter("confirmPassword");

        // =========================================
        // VALIDATION
        // =========================================

        if (currentPassword == null
                || newPassword == null
                || confirmPassword == null
                || currentPassword.trim().isEmpty()
                || newPassword.trim().isEmpty()
                || confirmPassword.trim().isEmpty()) {

            response.sendRedirect(
                    "profile/change-password.jsp?error=All Fields Required"
            );

            return;
        }

        // PASSWORD MATCH CHECK

        if (!newPassword.equals(confirmPassword)) {

            response.sendRedirect(
                    "profile/change-password.jsp?error=Passwords Do Not Match"
            );

            return;
        }

        if (!PasswordUtil.isStrongPassword(newPassword)) {

            response.sendRedirect(
                    "profile/change-password.jsp?error=Password Must Contain Uppercase Lowercase Number And Special Character"
            );

            return;
        }

        // =========================================
        // VERIFY CURRENT PASSWORD
        // =========================================

        boolean isValidPassword =
                userDAO.verifyCurrentPassword(
                        userId,
                        currentPassword
                );

        if (!isValidPassword) {

            response.sendRedirect(
                    "profile/change-password.jsp?error=Current Password Incorrect"
            );

            return;
        }

        // =========================================
        // UPDATE PASSWORD
        // =========================================

        boolean isPasswordUpdated =
                userDAO.changePassword(
                        userId,
                        newPassword
                );

        if (isPasswordUpdated) {

            response.sendRedirect(
                    "profile/change-password.jsp?success=Password Changed Successfully"
            );

        } else {

            response.sendRedirect(
                    "profile/change-password.jsp?error=Failed To Change Password"
            );
        }
    }
}
