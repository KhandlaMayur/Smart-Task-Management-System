package controller;

import dao.TaskDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {

        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // =========================================
        // SESSION VALIDATION
        // =========================================

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect("auth/login.jsp?error=Please Login First");
            return;
        }

        // =========================================
        // GET USER ID
        // =========================================

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString()
        );

        // =========================================
        // DASHBOARD STATISTICS
        // =========================================

        int totalTasks = taskDAO.getTotalTasks(userId);

        int completedTasks = taskDAO.getCompletedTasks(userId);

        int pendingTasks = taskDAO.getPendingTasks(userId);

        int inProgressTasks = taskDAO.getInProgressTasks(userId);

        int overdueTasks = taskDAO.getOverdueTasks(userId);

        int highPriorityTasks = taskDAO.getHighPriorityTasks(userId);

        // =========================================
        // CALCULATE PROGRESS
        // =========================================

        double progressPercentage = 0;

        if (totalTasks > 0) {

            progressPercentage =
                    ((double) completedTasks / totalTasks) * 100;
        }

        // =========================================
        // SET ATTRIBUTES
        // =========================================

        request.setAttribute("totalTasks", totalTasks);

        request.setAttribute("completedTasks", completedTasks);

        request.setAttribute("pendingTasks", pendingTasks);

        request.setAttribute("inProgressTasks", inProgressTasks);

        request.setAttribute("overdueTasks", overdueTasks);

        request.setAttribute("highPriorityTasks", highPriorityTasks);

        request.setAttribute("progressPercentage", progressPercentage);

        // =========================================
        // RECENT TASKS
        // =========================================

        request.setAttribute(
                "recentTasks",
                taskDAO.getRecentTasks(userId)
        );

        // =========================================
        // FORWARD TO DASHBOARD PAGE
        // =========================================

        request.getRequestDispatcher(
                "dashboard/dashboard.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
