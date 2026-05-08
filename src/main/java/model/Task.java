package model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Task {

    // =========================================
    // VARIABLES
    // =========================================

    private int id;

    private String title;

    private String description;

    private String priority;

    private String status;

    private Date dueDate;

    private int categoryId;

    private int userId;

    private String fileName;

    private LocalDateTime createdAt;

    // =========================================
    // DEFAULT CONSTRUCTOR
    // =========================================

    public Task() {
    }

    // =========================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================

    public Task(int id,
                String title,
                String description,
                String priority,
                String status,
                Date dueDate,
                int categoryId,
                int userId,
                String fileName,
                LocalDateTime createdAt) {

        this.id = id;

        this.title = title;

        this.description = description;

        this.priority = priority;

        this.status = status;

        this.dueDate = dueDate;

        this.categoryId = categoryId;

        this.userId = userId;

        this.fileName = fileName;

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

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getPriority() {

        return priority;
    }

    public void setPriority(String priority) {

        this.priority = priority;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public Date getDueDate() {

        return dueDate;
    }

    public void setDueDate(Date dueDate) {

        this.dueDate = dueDate;
    }

    public int getCategoryId() {

        return categoryId;
    }

    public void setCategoryId(int categoryId) {

        this.categoryId = categoryId;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getTaskFile() {

        return fileName;
    }

    public void setTaskFile(String taskFile) {

        this.fileName = taskFile;
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

        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", dueDate=" + dueDate +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", fileName='" + fileName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
