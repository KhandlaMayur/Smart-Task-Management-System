package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    // =========================================
    // CREATE USER SESSION
    // =========================================

    public static void createUserSession(
            HttpServletRequest request,
            int userId,
            String userName,
            String userEmail,
            String profileImage) {

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "userId",
                userId
        );

        session.setAttribute(
                "userName",
                userName
        );

        session.setAttribute(
                "userEmail",
                userEmail
        );

        session.setAttribute(
                "profileImage",
                profileImage
        );

        // SESSION TIMEOUT : 30 MINUTES

        session.setMaxInactiveInterval(
                30 * 60
        );
    }

    // =========================================
    // GET CURRENT SESSION
    // =========================================

    public static HttpSession getSession(
            HttpServletRequest request) {

        return request.getSession(false);
    }

    // =========================================
    // CHECK USER LOGGED IN
    // =========================================

    public static boolean isLoggedIn(
            HttpServletRequest request) {

        HttpSession session =
                getSession(request);

        return session != null
                && session.getAttribute("userId") != null;
    }

    // =========================================
    // GET USER ID
    // =========================================

    public static int getUserId(
            HttpServletRequest request) {

        HttpSession session =
                getSession(request);

        if (session != null
                && session.getAttribute("userId") != null) {

            return Integer.parseInt(
                    session.getAttribute("userId")
                            .toString()
            );
        }

        return 0;
    }

    // =========================================
    // GET USER NAME
    // =========================================

    public static String getUserName(
            HttpServletRequest request) {

        HttpSession session =
                getSession(request);

        if (session != null) {

            Object userName =
                    session.getAttribute("userName");

            if (userName != null) {

                return userName.toString();
            }
        }

        return "";
    }

    // =========================================
    // GET USER EMAIL
    // =========================================

    public static String getUserEmail(
            HttpServletRequest request) {

        HttpSession session =
                getSession(request);

        if (session != null) {

            Object userEmail =
                    session.getAttribute("userEmail");

            if (userEmail != null) {

                return userEmail.toString();
            }
        }

        return "";
    }

    // =========================================
    // GET PROFILE IMAGE
    // =========================================

    public static String getProfileImage(
            HttpServletRequest request) {

        HttpSession session =
                getSession(request);

        if (session != null) {

            Object profileImage =
                    session.getAttribute("profileImage");

            if (profileImage != null) {

                return profileImage.toString();
            }
        }

        return "default.png";
    }

    // =========================================
    // REMOVE SESSION ATTRIBUTE
    // =========================================

    public static void removeAttribute(
            HttpServletRequest request,
            String attributeName) {

        HttpSession session =
                getSession(request);

        if (session != null) {

            session.removeAttribute(attributeName);
        }
    }

    // =========================================
    // INVALIDATE SESSION
    // =========================================

    public static void destroySession(
            HttpServletRequest request) {

        HttpSession session =
                getSession(request);

        if (session != null) {

            session.invalidate();
        }
    }
}