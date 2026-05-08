package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter extends HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

        System.out.println("AuthFilter Initialized");
    }

    @Override
    public void doFilter(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        // =========================================
        // GET SESSION
        // =========================================

        HttpSession session =
                request.getSession(false);

        // =========================================
        // CHECK LOGIN
        // =========================================

        boolean isLoggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        // =========================================
        // GET REQUEST URI
        // =========================================

        String requestURI =
                request.getRequestURI();

        // =========================================
        // ALLOWED PUBLIC PATHS
        // =========================================

        boolean isLoginRequest =
                requestURI.contains("login.jsp")
                        || requestURI.contains("register.jsp")
                        || requestURI.contains("/login")
                        || requestURI.contains("/register");

        // =========================================
        // CACHE CONTROL
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
        // ACCESS CONTROL
        // =========================================

        if (isLoggedIn || isLoginRequest) {

            // USER AUTHENTICATED

            chain.doFilter(request, response);

        } else {

            // USER NOT LOGGED IN

            response.sendRedirect(
                    request.getContextPath()
                            + "/auth/login.jsp?error=Please Login First"
            );
        }
    }

    @Override
    public void destroy() {

        System.out.println("AuthFilter Destroyed");
    }
}
