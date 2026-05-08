package controller;

import dao.TaskDAO;
import model.Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class SearchTaskServlet extends HttpServlet {

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

            response.sendRedirect(
                    "auth/login.jsp?error=Please Login First"
            );

            return;
        }

        // =========================================
        // GET USER ID
        // =========================================

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString()
        );

        // =========================================
        // GET SEARCH PARAMETERS
        // =========================================

        String keyword =
                request.getParameter("keyword");

        String priority =
                request.getParameter("priority");

        String status =
                request.getParameter("status");

        String categoryIdStr =
                request.getParameter("categoryId");

        // =========================================
        // DEFAULT VALUES
        // =========================================

        if (keyword == null) {
            keyword = "";
        }

        if (priority == null) {
            priority = "";
        }

        if (status == null) {
            status = "";
        }

        int categoryId = 0;

        try {

            if (categoryIdStr != null
                    && !categoryIdStr.trim().isEmpty()) {

                categoryId =
                        Integer.parseInt(categoryIdStr);
            }

        } catch (NumberFormatException e) {

            categoryId = 0;
        }

        // =========================================
        // SEARCH TASKS
        // =========================================

        List<Task> taskList =
                taskDAO.searchTasks(
                        userId,
                        keyword,
                        priority,
                        status,
                        categoryId
                );

        // =========================================
        // SET ATTRIBUTES
        // =========================================

        request.setAttribute("taskList", taskList);

        request.setAttribute("keyword", keyword);

        request.setAttribute("priority", priority);

        request.setAttribute("status", status);

        request.setAttribute("categoryId", categoryId);

        // =========================================
        // RESULT MESSAGE
        // =========================================

        if (taskList == null || taskList.isEmpty()) {

            request.setAttribute(
                    "message",
                    "No Tasks Found"
            );
        }

        // =========================================
        // FORWARD TO JSP
        // =========================================

        request.getRequestDispatcher(
                "task/search-task.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
