package model;

import java.sql.Timestamp;

public class Reminder {

    // =========================================
    // VARIABLES
    // =========================================

    private int id;

    private int taskId;

    private Timestamp reminderTime;

    private String message;

    private String status;

    // =========================================
    // DEFAULT CONSTRUCTOR
    // =========================================

    public Reminder() {
    }

    // =========================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================

    public Reminder(int id,
                    int taskId,
                    Timestamp reminderTime,
                    String message,
                    String status) {

        this.id = id;

        this.taskId = taskId;

        this.reminderTime = reminderTime;

        this.message = message;

        this.status = status;
    }

    // =========================================
    // GETTERS AND SETTERS
    // =========================================

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getTaskId() {

        return taskId;
    }

    public void setTaskId(int taskId) {

        this.taskId = taskId;
    }

    public Timestamp getReminderTime() {

        return reminderTime;
    }

    public void setReminderTime(Timestamp reminderTime) {

        this.reminderTime = reminderTime;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    // =========================================
    // TO STRING METHOD
    // =========================================

    @Override
    public String toString() {

        return "Reminder{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", reminderTime=" + reminderTime +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}