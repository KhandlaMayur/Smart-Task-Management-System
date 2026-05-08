package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // =========================================
        // GET CURRENT SESSION
        // =========================================

        HttpSession session = request.getSession(false);

        // =========================================
        // INVALIDATE SESSION
        // =========================================

        if (session != null) {

            session.removeAttribute("userId");

            session.removeAttribute("userName");

            session.removeAttribute("userEmail");

            session.invalidate();
        }

        // =========================================
        // CLEAR CACHE
        // =========================================

        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        response.setHeader(
                "Pragma",
                "no-cache"
        );

        response.setDateHeader(
                "Expires",
                0
        );

        // =========================================
        // REDIRECT LOGIN PAGE
        // =========================================

        response.sendRedirect(
                "auth/login.jsp?success=Logged Out Successfully"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
