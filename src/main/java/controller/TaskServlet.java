package controller;

import dao.TaskDAO;
import dao.ReminderDAO;
import model.Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class TaskServlet extends HttpServlet {

    private TaskDAO taskDAO;

    private ReminderDAO reminderDAO;

    @Override
    public void init() throws ServletException {

        taskDAO = new TaskDAO();
        reminderDAO = new ReminderDAO();
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
        // GET ACTION
        // =========================================

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            action = "list";
        }

        switch (action) {

            // =========================================
            // LIST TASKS
            // =========================================

            case "list":

                showTaskList(request, userId);

                request.getRequestDispatcher(
                        "/task/task-list.jsp"
                ).forward(request, response);

                break;

            // =========================================
            // VIEW TASK DETAILS
            // =========================================

            case "view":

                viewTask(request, response);

                break;

            // =========================================
            // COMPLETE TASK
            // =========================================

            case "complete":

                completeTask(request, response);

                break;

            // =========================================
            // DELETE TASK
            // =========================================

            case "delete":

                deleteTask(request, response);

                break;

            default:

                response.sendRedirect(
                        request.getContextPath()
                                + "/task?action=list&error=Invalid Action"
                );
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    private void showTaskList(HttpServletRequest request,
                              int userId) {

        String statusFilter =
                request.getParameter("status");

        List<Task> taskList;

        String pageTitle =
                "Task List";

        String pageDescription =
                "Manage And Track Your Tasks";

        String normalizedStatusFilter =
                "";

        if (statusFilter == null
                || statusFilter.trim().isEmpty()
                || "All".equalsIgnoreCase(statusFilter)) {

            taskList =
                    taskDAO.getAllTasks(userId);

        } else if ("Completed".equalsIgnoreCase(statusFilter)) {

            taskList =
                    taskDAO.getCompletedTaskList(userId);

            normalizedStatusFilter =
                    "Completed";

            pageTitle =
                    "Completed Tasks";

            pageDescription =
                    "Showing Only Completed Tasks";

        } else if ("Pending".equalsIgnoreCase(statusFilter)) {

            taskList =
                    taskDAO.getPendingTaskList(userId);

            normalizedStatusFilter =
                    "Pending";

            pageTitle =
                    "Pending Tasks";

            pageDescription =
                    "Showing Only Pending Tasks";

        } else if ("In Progress".equalsIgnoreCase(statusFilter)
                || "In Process".equalsIgnoreCase(statusFilter)) {

            taskList =
                    taskDAO.getInProgressTaskList(userId);

            normalizedStatusFilter =
                    "In Progress";

            pageTitle =
                    "In Progress Tasks";

            pageDescription =
                    "Showing Only In Progress Tasks";

        } else {

            taskList =
                    taskDAO.getAllTasks(userId);
        }

        request.setAttribute(
                "taskList",
                taskList
        );

        request.setAttribute(
                "currentStatusFilter",
                normalizedStatusFilter
        );

        request.setAttribute(
                "pageTitle",
                pageTitle
        );

        request.setAttribute(
                "pageDescription",
                pageDescription
        );
    }

    // =========================================
    // VIEW TASK DETAILS
    // =========================================

    private void viewTask(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Invalid Task ID"
            );

            return;
        }

        int taskId;

        try {

            taskId = Integer.parseInt(idStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Task ID Must Be Numeric"
            );

            return;
        }

        // =========================================
        // GET TASK
        // =========================================

        Task task =
                taskDAO.getTaskById(taskId);

        if (task == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Task Not Found"
            );

            return;
        }

        // =========================================
        // SET ATTRIBUTE
        // =========================================

        request.setAttribute("task", task);

        // =========================================
        // FORWARD
        // =========================================

        request.getRequestDispatcher(
                "/task/task-details.jsp"
        ).forward(request, response);
    }

    // =========================================
    // COMPLETE TASK
    // =========================================

    private void completeTask(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Invalid Task ID"
            );

            return;
        }

        int taskId;

        try {

            taskId = Integer.parseInt(idStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Task ID Must Be Numeric"
            );

            return;
        }

        // =========================================
        // UPDATE STATUS
        // =========================================

        boolean isCompleted =
                taskDAO.markTaskCompleted(taskId);

        if (isCompleted) {

            reminderDAO.markRemindersCompletedByTask(taskId);

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&success=Task Marked As Completed"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Failed To Update Task"
            );
        }
    }

    // =========================================
    // DELETE TASK
    // =========================================

    private void deleteTask(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Invalid Task ID"
            );

            return;
        }

        int taskId;

        try {

            taskId = Integer.parseInt(idStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Task ID Must Be Numeric"
            );

            return;
        }

        // =========================================
        // DELETE TASK
        // =========================================

        boolean isDeleted =
                taskDAO.deleteTask(taskId);

        if (isDeleted) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&success=Task Deleted Successfully"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Failed To Delete Task"
            );
        }
    }
}
