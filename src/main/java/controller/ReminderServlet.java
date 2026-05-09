package controller;

import dao.ReminderDAO;
import dao.TaskDAO;
import model.Reminder;
import model.Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReminderServlet extends HttpServlet {

    private ReminderDAO reminderDAO;

    private TaskDAO taskDAO;

    @Override
    public void init() throws ServletException {

        reminderDAO = new ReminderDAO();
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

        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    "auth/login.jsp?error=Please Login First"
            );

            return;
        }

        // =========================================
        // GET ACTION
        // =========================================

        int userId = Integer.parseInt(
                session.getAttribute("userId").toString()
        );

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Invalid Action"
            );

            return;
        }

        switch (action) {

            case "add":
                addReminder(request, response, userId);
                break;

            case "update":
                updateReminder(request, response, userId);
                break;

            case "delete":
                deleteReminder(request, response, userId);
                break;

            default:
                response.sendRedirect(
                        request.getContextPath()
                                + "/reminder/reminder-list.jsp?error=Unknown Action"
                );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    // =========================================
    // ADD REMINDER
    // =========================================

    private void addReminder(HttpServletRequest request,
                             HttpServletResponse response,
                             int userId)
            throws IOException {

        String taskIdStr = request.getParameter("taskId");

        String reminderTimeStr =
                request.getParameter("reminderTime");

        String reminderMessage =
                request.getParameter("message");

        // =========================================
        // VALIDATION
        // =========================================

        if (taskIdStr == null || taskIdStr.trim().isEmpty()
                || reminderTimeStr == null || reminderTimeStr.trim().isEmpty()
                || reminderMessage == null || reminderMessage.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/add-reminder.jsp?error=All Fields Are Required"
            );

            return;
        }

        int taskId;

        try {

            taskId = Integer.parseInt(taskIdStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/add-reminder.jsp?error=Invalid Task ID"
            );

            return;
        }

        Task task =
                taskDAO.getTaskByIdAndUserId(
                        taskId,
                        userId
                );

        if (task == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/add-reminder.jsp?error=Task Not Found"
            );

            return;
        }

        if (!"Pending".equalsIgnoreCase(task.getStatus())) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/add-reminder.jsp?error=Reminder Can Be Set Only For Pending Tasks"
                            + "&taskId=" + taskId
            );

            return;
        }

        // =========================================
        // CONVERT DATE TIME
        // =========================================

        Timestamp reminderTime;

        try {

            LocalDateTime localDateTime =
                    LocalDateTime.parse(reminderTimeStr);

            reminderTime =
                    Timestamp.valueOf(localDateTime);

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/add-reminder.jsp?error=Invalid Reminder Date Or Time"
                            + "&taskId=" + taskId
            );

            return;
        }

        // =========================================
        // CREATE REMINDER OBJECT
        // =========================================

        Reminder reminder = new Reminder();

        reminder.setTaskId(taskId);

        reminder.setReminderTime(reminderTime);

        reminder.setMessage(reminderMessage);

        reminder.setStatus("Pending");

        // =========================================
        // SAVE REMINDER
        // =========================================

        boolean isAdded =
                reminderDAO.addReminder(reminder);

        if (isAdded) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?success=Reminder Added Successfully"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/add-reminder.jsp?error=Failed To Add Reminder"
                            + "&taskId=" + taskId
            );
        }
    }

    // =========================================
    // UPDATE REMINDER
    // =========================================

    private void updateReminder(HttpServletRequest request,
                                HttpServletResponse response,
                                int userId)
            throws IOException {

        String idStr = request.getParameter("id");

        String reminderTimeStr =
                request.getParameter("reminderTime");

        String reminderMessage =
                request.getParameter("message");

        String status =
                request.getParameter("status");

        // =========================================
        // VALIDATION
        // =========================================

        if (idStr == null || idStr.trim().isEmpty()
                || reminderTimeStr == null || reminderTimeStr.trim().isEmpty()
                || reminderMessage == null || reminderMessage.trim().isEmpty()
                || status == null || status.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=All Fields Required"
            );

            return;
        }

        int reminderId;

        try {

            reminderId = Integer.parseInt(idStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Invalid Reminder ID"
            );

            return;
        }

        Reminder existingReminder =
                reminderDAO.getReminderByIdForUser(
                        reminderId,
                        userId
                );

        if (existingReminder == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Reminder Not Found"
            );

            return;
        }

        if (!"Pending".equalsIgnoreCase(status)
                && !"Completed".equalsIgnoreCase(status)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Invalid Reminder Status"
            );

            return;
        }

        // =========================================
        // CONVERT TIMESTAMP
        // =========================================

        Timestamp reminderTime;

        try {

            LocalDateTime localDateTime =
                    LocalDateTime.parse(reminderTimeStr);

            reminderTime =
                    Timestamp.valueOf(localDateTime);

        } catch (Exception e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Invalid Reminder Date Or Time"
            );

            return;
        }

        // =========================================
        // CREATE OBJECT
        // =========================================

        Reminder reminder = new Reminder();

        reminder.setId(reminderId);

        reminder.setReminderTime(reminderTime);

        reminder.setMessage(reminderMessage);

        reminder.setStatus(status);

        // =========================================
        // UPDATE REMINDER
        // =========================================

        boolean isUpdated =
                reminderDAO.updateReminder(reminder);

        if (isUpdated
                && "Completed".equalsIgnoreCase(status)) {

            taskDAO.markTaskCompleted(
                    existingReminder.getTaskId()
            );

            reminderDAO.markRemindersCompletedByTask(
                    existingReminder.getTaskId()
            );
        }

        if (isUpdated) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?success=Reminder Updated Successfully"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Failed To Update Reminder"
            );
        }
    }

    // =========================================
    // DELETE REMINDER
    // =========================================

    private void deleteReminder(HttpServletRequest request,
                                HttpServletResponse response,
                                int userId)
            throws IOException {

        String idStr = request.getParameter("id");

        // =========================================
        // VALIDATION
        // =========================================

        if (idStr == null || idStr.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Invalid Reminder ID"
            );

            return;
        }

        int reminderId;

        try {

            reminderId = Integer.parseInt(idStr);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Reminder ID Must Be Numeric"
            );

            return;
        }

        Reminder reminder =
                reminderDAO.getReminderByIdForUser(
                        reminderId,
                        userId
                );

        if (reminder == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Reminder Not Found"
            );

            return;
        }

        // =========================================
        // DELETE REMINDER
        // =========================================

        boolean isDeleted =
                reminderDAO.deleteReminder(reminderId);

        if (isDeleted) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?success=Reminder Deleted Successfully"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/reminder/reminder-list.jsp?error=Failed To Delete Reminder"
            );
        }
    }
}
