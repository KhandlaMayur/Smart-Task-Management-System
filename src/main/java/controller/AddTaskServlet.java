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
import java.time.LocalDate;
import java.time.LocalDateTime;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AddTaskServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "uploads/task-files";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // =========================
        // SESSION VALIDATION
        // =========================
        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect("auth/login.jsp?error=Please Login First");
            return;
        }

        int userId = Integer.parseInt(session.getAttribute("userId").toString());

        // =========================
        // GET FORM DATA
        // =========================
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priority = request.getParameter("priority");
        String status = request.getParameter("status");
        String dueDateStr = request.getParameter("dueDate");
        String categoryIdStr = request.getParameter("categoryId");

        // =========================
        // VALIDATION
        // =========================
        if (title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || dueDateStr == null || dueDateStr.trim().isEmpty()
                || categoryIdStr == null || categoryIdStr.trim().isEmpty()) {

            response.sendRedirect("task/add-task.jsp?error=All Fields Are Required");
            return;
        }

        // =========================
        // CONVERT VALUES
        // =========================
        int categoryId = Integer.parseInt(categoryIdStr);

        Date dueDate = Date.valueOf(dueDateStr);

        // =========================
        // FILE UPLOAD
        // =========================
        Part filePart = request.getPart("taskFile");

        String fileName = "";

        if (filePart != null && filePart.getSize() > 0) {

            fileName = Paths.get(filePart.getSubmittedFileName())
                    .getFileName()
                    .toString();

            // Get Upload Path
            String applicationPath = request.getServletContext().getRealPath("");

            String uploadPath = applicationPath + File.separator + UPLOAD_DIRECTORY;

            File uploadDir = new File(uploadPath);

            // Create Folder If Not Exist
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save File
            filePart.write(uploadPath + File.separator + fileName);
        }

        // =========================
        // AUTO PRIORITY LOGIC
        // =========================
        LocalDate today = LocalDate.now();
        LocalDate dueLocalDate = dueDate.toLocalDate();

        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(today, dueLocalDate);

        if (daysBetween <= 2) {
            priority = "High";
        }

        // =========================
        // CREATE TASK OBJECT
        // =========================
        Task task = new Task();

        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setStatus(status);
        task.setDueDate(dueDate);
        task.setCategoryId(categoryId);
        task.setUserId(userId);
        task.setFileName(fileName);
        task.setCreatedAt(LocalDateTime.now());

        // =========================
        // SAVE TASK
        // =========================
        TaskDAO taskDAO = new TaskDAO();

        boolean isInserted = taskDAO.addTask(task);

        // =========================
        // RESPONSE
        // =========================
        if (isInserted) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/task?action=list&success=Task Added Successfully"
            );

        } else {

            response.sendRedirect("task/add-task.jsp?error=Failed To Add Task");
        }
    }
}
