package controller;

import dao.TaskDAO;
import model.Task;

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
import java.sql.Date;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UpdateTaskServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY =
            "uploads/task-files";

    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {

        taskDAO = new TaskDAO();
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

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    "auth/login.jsp?error=Please Login First"
            );

            return;
        }

        // =========================================
        // GET FORM DATA
        // =========================================

        String idStr =
                request.getParameter("id");

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String priority =
                request.getParameter("priority");

        String status =
                request.getParameter("status");

        String dueDateStr =
                request.getParameter("dueDate");

        String categoryIdStr =
                request.getParameter("categoryId");

        // =========================================
        // VALIDATION
        // =========================================

        if (idStr == null || idStr.trim().isEmpty()
                || title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || priority == null || priority.trim().isEmpty()
                || status == null || status.trim().isEmpty()
                || dueDateStr == null || dueDateStr.trim().isEmpty()
                || categoryIdStr == null || categoryIdStr.trim().isEmpty()) {

            response.sendRedirect(
                    "task/edit-task.jsp?error=All Fields Are Required"
            );

            return;
        }

        int taskId;
        int categoryId;

        try {

            taskId = Integer.parseInt(idStr);

            categoryId = Integer.parseInt(categoryIdStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    "task/edit-task.jsp?error=Invalid Numeric Value"
            );

            return;
        }

        // =========================================
        // DATE CONVERSION
        // =========================================

        Date dueDate;

        try {

            dueDate = Date.valueOf(dueDateStr);

        } catch (Exception e) {

            response.sendRedirect(
                    "task/edit-task.jsp?error=Invalid Date Format"
            );

            return;
        }

        // =========================================
        // FILE UPLOAD
        // =========================================

        Part filePart =
                request.getPart("taskFile");

        String fileName = "";

        // GET OLD FILE NAME

        String oldFileName =
                taskDAO.getTaskFileName(taskId);

        if (filePart != null
                && filePart.getSize() > 0) {

            fileName =
                    Paths.get(
                            filePart.getSubmittedFileName()
                    ).getFileName().toString();

            String applicationPath =
                    request.getServletContext()
                            .getRealPath("");

            String uploadPath =
                    applicationPath
                            + File.separator
                            + UPLOAD_DIRECTORY;

            File uploadDir =
                    new File(uploadPath);

            // CREATE DIRECTORY

            if (!uploadDir.exists()) {

                uploadDir.mkdirs();
            }

            // SAVE FILE

            filePart.write(
                    uploadPath
                            + File.separator
                            + fileName
            );

            // DELETE OLD FILE

            if (oldFileName != null
                    && !oldFileName.trim().isEmpty()) {

                File oldFile =
                        new File(
                                uploadPath
                                        + File.separator
                                        + oldFileName
                        );

                if (oldFile.exists()) {

                    oldFile.delete();
                }
            }

        } else {

            // KEEP OLD FILE

            fileName = oldFileName;
        }

        // =========================================
        // CREATE TASK OBJECT
        // =========================================

        Task task = new Task();

        task.setId(taskId);

        task.setTitle(title);

        task.setDescription(description);

        task.setPriority(priority);

        task.setStatus(status);

        task.setDueDate(dueDate);

        task.setCategoryId(categoryId);

        task.setFileName(fileName);

        // =========================================
        // UPDATE TASK
        // =========================================

        boolean isUpdated =
                taskDAO.updateTask(task);

        if (isUpdated) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&success=Task Updated Successfully"
            );

        } else {

            response.sendRedirect(
                    "task/edit-task.jsp?error=Failed To Update Task"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}
