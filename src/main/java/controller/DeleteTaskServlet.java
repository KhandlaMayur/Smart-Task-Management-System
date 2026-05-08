package controller;

import dao.TaskDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

public class DeleteTaskServlet extends HttpServlet {

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
        // GET TASK ID
        // =========================================

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
        // GET FILE NAME BEFORE DELETE
        // =========================================

        String fileName = taskDAO.getTaskFileName(taskId);

        // =========================================
        // DELETE TASK FROM DATABASE
        // =========================================

        boolean isDeleted = taskDAO.deleteTask(taskId);

        if (isDeleted) {

            // =========================================
            // DELETE ATTACHED FILE
            // =========================================

            if (fileName != null && !fileName.trim().isEmpty()) {

                String uploadPath =
                        getServletContext().getRealPath("")
                                + File.separator
                                + "uploads"
                                + File.separator
                                + "task-files";

                File file = new File(uploadPath + File.separator + fileName);

                if (file.exists()) {

                    file.delete();
                }
            }

            // =========================================
            // SUCCESS
            // =========================================

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&success=Task Deleted Successfully"
            );

        } else {

            // =========================================
            // FAILED
            // =========================================

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&error=Failed To Delete Task"
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
