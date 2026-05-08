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

public class TaskServlet extends HttpServlet {

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

                List<Task> taskList =
                        taskDAO.getAllTasks(userId);

                request.setAttribute(
                        "taskList",
                        taskList
                );

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
