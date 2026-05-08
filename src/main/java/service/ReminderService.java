package service;

import dao.ReminderDAO;
import model.Reminder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class ReminderService {

    private ReminderDAO reminderDAO;

    // =========================================
    // CONSTRUCTOR
    // =========================================

    public ReminderService() {

        reminderDAO = new ReminderDAO();
    }

    // =========================================
    // ADD REMINDER
    // =========================================

    public boolean addReminder(Reminder reminder) {

        return reminderDAO.addReminder(reminder);
    }

    // =========================================
    // UPDATE REMINDER
    // =========================================

    public boolean updateReminder(Reminder reminder) {

        return reminderDAO.updateReminder(reminder);
    }

    // =========================================
    // DELETE REMINDER
    // =========================================

    public boolean deleteReminder(int id) {

        return reminderDAO.deleteReminder(id);
    }

    // =========================================
    // GET REMINDER BY ID
    // =========================================

    public Reminder getReminderById(int id) {

        return reminderDAO.getReminderById(id);
    }

    // =========================================
    // GET ALL REMINDERS
    // =========================================

    public List<Reminder> getAllReminders() {

        return reminderDAO.getAllReminders();
    }

    // =========================================
    // GET TASK REMINDERS
    // =========================================

    public List<Reminder> getTaskReminders(int taskId) {

        return reminderDAO.getRemindersByTask(taskId);
    }

    // =========================================
    // GET UPCOMING REMINDERS
    // =========================================

    public List<Reminder> getUpcomingReminders() {

        List<Reminder> upcomingList =
                new ArrayList<>();

        try {

            List<Reminder> reminderList =
                    reminderDAO.getPendingReminders();

            LocalDateTime now =
                    LocalDateTime.now();

            LocalDateTime next24Hours =
                    now.plusHours(24);

            for (Reminder reminder : reminderList) {

                Timestamp reminderTime =
                        reminder.getReminderTime();

                LocalDateTime reminderDateTime =
                        reminderTime.toLocalDateTime();

                if (reminderDateTime.isAfter(now)
                        && reminderDateTime.isBefore(next24Hours)) {

                    upcomingList.add(reminder);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return upcomingList;
    }

    // =========================================
    // GET OVERDUE REMINDERS
    // =========================================

    public List<Reminder> getOverdueReminders() {

        List<Reminder> overdueList =
                new ArrayList<>();

        try {

            List<Reminder> reminderList =
                    reminderDAO.getPendingReminders();

            LocalDateTime now =
                    LocalDateTime.now();

            for (Reminder reminder : reminderList) {

                Timestamp reminderTime =
                        reminder.getReminderTime();

                LocalDateTime reminderDateTime =
                        reminderTime.toLocalDateTime();

                if (reminderDateTime.isBefore(now)) {

                    overdueList.add(reminder);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return overdueList;
    }

    // =========================================
    // MARK REMINDER COMPLETED
    // =========================================

    public boolean markReminderCompleted(int id) {

        return reminderDAO.markReminderCompleted(id);
    }

    // =========================================
    // GET TOTAL REMINDERS
    // =========================================

    public int getTotalReminders() {

        return reminderDAO.getTotalReminders();
    }

    // =========================================
    // CHECK REMINDER DUE
    // =========================================

    public boolean isReminderDue(Reminder reminder) {

        try {

            LocalDateTime now =
                    LocalDateTime.now();

            LocalDateTime reminderTime =
                    reminder.getReminderTime()
                            .toLocalDateTime();

            return reminderTime.isBefore(now)
                    || reminderTime.isEqual(now);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}