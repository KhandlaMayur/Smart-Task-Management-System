package dao;

import model.Reminder;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class ReminderDAO {

    // =========================================
    // ADD REMINDER
    // =========================================

    public boolean addReminder(Reminder reminder) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO reminders(task_id, reminder_time, message, status) VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(
                    1,
                    reminder.getTaskId()
            );

            ps.setTimestamp(
                    2,
                    reminder.getReminderTime()
            );

            ps.setString(
                    3,
                    reminder.getMessage()
            );

            ps.setString(
                    4,
                    reminder.getStatus()
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // UPDATE REMINDER
    // =========================================

    public boolean updateReminder(Reminder reminder) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE reminders SET reminder_time = ?, message = ?, status = ? WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setTimestamp(
                    1,
                    reminder.getReminderTime()
            );

            ps.setString(
                    2,
                    reminder.getMessage()
            );

            ps.setString(
                    3,
                    reminder.getStatus()
            );

            ps.setInt(
                    4,
                    reminder.getId()
            );

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // DELETE REMINDER
    // =========================================

    public boolean deleteReminder(int id) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM reminders WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // GET REMINDER BY ID
    // =========================================

    public Reminder getReminderById(int id) {

        Reminder reminder = null;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM reminders WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                reminder = new Reminder();

                reminder.setId(
                        rs.getInt("id")
                );

                reminder.setTaskId(
                        rs.getInt("task_id")
                );

                reminder.setReminderTime(
                        rs.getTimestamp("reminder_time")
                );

                reminder.setMessage(
                        rs.getString("message")
                );

                reminder.setStatus(
                        rs.getString("status")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reminder;
    }

    public Reminder getReminderByIdForUser(int id,
                                           int userId) {

        Reminder reminder = null;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT r.* FROM reminders r INNER JOIN tasks t ON r.task_id = t.id WHERE r.id = ? AND t.user_id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.setInt(2, userId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                reminder = extractReminderFromResultSet(rs);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reminder;
    }

    // =========================================
    // GET ALL REMINDERS
    // =========================================

    public List<Reminder> getAllReminders() {

        List<Reminder> reminderList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM reminders ORDER BY reminder_time ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Reminder reminder =
                        new Reminder();

                reminder.setId(
                        rs.getInt("id")
                );

                reminder.setTaskId(
                        rs.getInt("task_id")
                );

                reminder.setReminderTime(
                        rs.getTimestamp("reminder_time")
                );

                reminder.setMessage(
                        rs.getString("message")
                );

                reminder.setStatus(
                        rs.getString("status")
                );

                reminderList.add(reminder);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reminderList;
    }

    public List<Reminder> getAllRemindersByUser(int userId) {

        List<Reminder> reminderList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT r.* FROM reminders r INNER JOIN tasks t ON r.task_id = t.id WHERE t.user_id = ? ORDER BY r.reminder_time ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                reminderList.add(
                        extractReminderFromResultSet(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reminderList;
    }

    // =========================================
    // GET REMINDERS BY TASK
    // =========================================

    public List<Reminder> getRemindersByTask(int taskId) {

        List<Reminder> reminderList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM reminders WHERE task_id = ? ORDER BY reminder_time ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, taskId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Reminder reminder =
                        new Reminder();

                reminder.setId(
                        rs.getInt("id")
                );

                reminder.setTaskId(
                        rs.getInt("task_id")
                );

                reminder.setReminderTime(
                        rs.getTimestamp("reminder_time")
                );

                reminder.setMessage(
                        rs.getString("message")
                );

                reminder.setStatus(
                        rs.getString("status")
                );

                reminderList.add(reminder);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reminderList;
    }

    // =========================================
    // GET PENDING REMINDERS
    // =========================================

    public List<Reminder> getPendingReminders() {

        List<Reminder> reminderList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM reminders WHERE status = 'Pending' ORDER BY reminder_time ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Reminder reminder =
                        new Reminder();

                reminder.setId(
                        rs.getInt("id")
                );

                reminder.setTaskId(
                        rs.getInt("task_id")
                );

                reminder.setReminderTime(
                        rs.getTimestamp("reminder_time")
                );

                reminder.setMessage(
                        rs.getString("message")
                );

                reminder.setStatus(
                        rs.getString("status")
                );

                reminderList.add(reminder);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return reminderList;
    }

    // =========================================
    // MARK REMINDER COMPLETED
    // =========================================

    public boolean markReminderCompleted(int id) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE reminders SET status = 'Completed' WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            int rows =
                    ps.executeUpdate();

            if (rows > 0) {

                status = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    public boolean markRemindersCompletedByTask(int taskId) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE reminders SET status = 'Completed' WHERE task_id = ? AND status <> 'Completed'";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, taskId);

            ps.executeUpdate();

            status = true;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    // =========================================
    // GET TOTAL REMINDERS
    // =========================================

    public int getTotalReminders() {

        int count = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM reminders";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                count = rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return count;
    }

    private Reminder extractReminderFromResultSet(ResultSet rs)
            throws Exception {

        Reminder reminder =
                new Reminder();

        reminder.setId(
                rs.getInt("id")
        );

        reminder.setTaskId(
                rs.getInt("task_id")
        );

        reminder.setReminderTime(
                rs.getTimestamp("reminder_time")
        );

        reminder.setMessage(
                rs.getString("message")
        );

        reminder.setStatus(
                rs.getString("status")
        );

        return reminder;
    }
}
