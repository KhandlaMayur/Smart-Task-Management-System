package model;

import java.time.LocalDateTime;

public class ActivityLog {

    // =========================================
    // VARIABLES
    // =========================================

    private int id;

    private int userId;

    private String action;

    private LocalDateTime createdAt;

    // =========================================
    // DEFAULT CONSTRUCTOR
    // =========================================

    public ActivityLog() {
    }

    // =========================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================

    public ActivityLog(int id,
                       int userId,
                       String action,
                       LocalDateTime createdAt) {

        this.id = id;

        this.userId = userId;

        this.action = action;

        this.createdAt = createdAt;
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

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public String getAction() {

        return action;
    }

    public void setAction(String action) {

        this.action = action;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    // =========================================
    // TO STRING METHOD
    // =========================================

    @Override
    public String toString() {

        return "ActivityLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", action='" + action + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}