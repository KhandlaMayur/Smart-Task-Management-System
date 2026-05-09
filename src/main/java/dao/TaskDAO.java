package dao;

import model.Task;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // =========================================
    // ADD TASK
    // =========================================

    public boolean addTask(Task task) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO tasks(title, description, priority, status, due_date, category_id, user_id, task_file) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, task.getTitle());

            ps.setString(2, task.getDescription());

            ps.setString(3, task.getPriority());

            ps.setString(4, task.getStatus());

            ps.setDate(5, task.getDueDate());

            ps.setInt(6, task.getCategoryId());

            ps.setInt(7, task.getUserId());

            ps.setString(8, task.getFileName());

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
    // UPDATE TASK
    // =========================================

    public boolean updateTask(Task task) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE tasks SET title = ?, description = ?, priority = ?, status = ?, due_date = ?, category_id = ?, task_file = ? WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, task.getTitle());

            ps.setString(2, task.getDescription());

            ps.setString(3, task.getPriority());

            ps.setString(4, task.getStatus());

            ps.setDate(5, task.getDueDate());

            ps.setInt(6, task.getCategoryId());

            ps.setString(7, task.getFileName());

            ps.setInt(8, task.getId());

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
    // DELETE TASK
    // =========================================

    public boolean deleteTask(int id) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM tasks WHERE id = ?";

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
    // GET TASK BY ID
    // =========================================

    public Task getTaskById(int id) {

        Task task = null;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM tasks WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                task = extractTaskFromResultSet(rs);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return task;
    }

    // =========================================
    // GET ALL TASKS
    // =========================================

    public List<Task> getAllTasks(int userId) {

        List<Task> taskList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM tasks WHERE user_id = ? ORDER BY due_date ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                taskList.add(
                        extractTaskFromResultSet(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return taskList;
    }

    public Task getTaskByIdAndUserId(int id,
                                     int userId) {

        Task task = null;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM tasks WHERE id = ? AND user_id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.setInt(2, userId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                task = extractTaskFromResultSet(rs);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return task;
    }

    public boolean isTaskOwnedByUser(int taskId,
                                     int userId) {

        return getTaskByIdAndUserId(taskId, userId) != null;
    }

    public List<Task> getPendingTaskList(int userId) {

        return getTasksByStatus(userId, "Pending");
    }

    public List<Task> getCompletedTaskList(int userId) {

        return getTasksByStatus(userId, "Completed");
    }

    public List<Task> getInProgressTaskList(int userId) {

        return getTasksByStatus(userId, "In Progress");
    }

    // =========================================
    // SEARCH TASKS
    // =========================================

    public List<Task> searchTasks(int userId,
                                  String keyword,
                                  String priority,
                                  String status,
                                  int categoryId) {

        List<Task> taskList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            StringBuilder sql =
                    new StringBuilder(
                            "SELECT * FROM tasks WHERE user_id = ?"
                    );

            if (keyword != null && !keyword.isEmpty()) {

                sql.append(
                        " AND (title LIKE ? OR description LIKE ?)"
                );
            }

            if (priority != null && !priority.isEmpty()) {

                sql.append(
                        " AND priority = ?"
                );
            }

            if (status != null && !status.isEmpty()) {

                sql.append(
                        " AND status = ?"
                );
            }

            if (categoryId > 0) {

                sql.append(
                        " AND category_id = ?"
                );
            }

            sql.append(
                    " ORDER BY due_date ASC"
            );

            PreparedStatement ps =
                    conn.prepareStatement(sql.toString());

            int parameterIndex = 1;

            ps.setInt(parameterIndex++, userId);

            if (keyword != null && !keyword.isEmpty()) {

                ps.setString(
                        parameterIndex++,
                        "%" + keyword + "%"
                );

                ps.setString(
                        parameterIndex++,
                        "%" + keyword + "%"
                );
            }

            if (priority != null && !priority.isEmpty()) {

                ps.setString(
                        parameterIndex++,
                        priority
                );
            }

            if (status != null && !status.isEmpty()) {

                ps.setString(
                        parameterIndex++,
                        status
                );
            }

            if (categoryId > 0) {

                ps.setInt(
                        parameterIndex++,
                        categoryId
                );
            }

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                taskList.add(
                        extractTaskFromResultSet(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return taskList;
    }

    // =========================================
    // MARK TASK COMPLETED
    // =========================================

    public boolean markTaskCompleted(int id) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "UPDATE tasks SET status = 'Completed' WHERE id = ?";

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
    // DASHBOARD METHODS
    // =========================================

    public int getTotalTasks(int userId) {

        return getTaskCount(
                "SELECT COUNT(*) FROM tasks WHERE user_id = ?",
                userId
        );
    }

    public int getCompletedTasks(int userId) {

        return getTaskCount(
                "SELECT COUNT(*) FROM tasks WHERE user_id = ? AND status = 'Completed'",
                userId
        );
    }

    public int getPendingTasks(int userId) {

        return getTaskCount(
                "SELECT COUNT(*) FROM tasks WHERE user_id = ? AND status = 'Pending'",
                userId
        );
    }

    public int getInProgressTasks(int userId) {

        return getTaskCount(
                "SELECT COUNT(*) FROM tasks WHERE user_id = ? AND status = 'In Progress'",
                userId
        );
    }

    public int getOverdueTasks(int userId) {

        return getTaskCount(
                "SELECT COUNT(*) FROM tasks WHERE user_id = ? AND due_date < CURDATE() AND status != 'Completed'",
                userId
        );
    }

    public int getHighPriorityTasks(int userId) {

        return getTaskCount(
                "SELECT COUNT(*) FROM tasks WHERE user_id = ? AND priority = 'High'",
                userId
        );
    }

    // =========================================
    // RECENT TASKS
    // =========================================

    public List<Task> getRecentTasks(int userId) {

        List<Task> taskList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM tasks WHERE user_id = ? ORDER BY created_at DESC LIMIT 5";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                taskList.add(
                        extractTaskFromResultSet(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return taskList;
    }

    // =========================================
    // GET FILE NAME
    // =========================================

    public String getTaskFileName(int taskId) {

        String fileName = "";

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT task_file FROM tasks WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, taskId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                fileName =
                        rs.getString("task_file");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return fileName;
    }

    private List<Task> getTasksByStatus(int userId,
                                        String status) {

        List<Task> taskList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM tasks WHERE user_id = ? AND status = ? ORDER BY due_date ASC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ps.setString(2, status);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                taskList.add(
                        extractTaskFromResultSet(rs)
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return taskList;
    }

    // =========================================
    // COMMON COUNT METHOD
    // =========================================

    private int getTaskCount(String sql,
                             int userId) {

        int count = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

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

    // =========================================
    // EXTRACT TASK OBJECT
    // =========================================

    private Task extractTaskFromResultSet(ResultSet rs)
            throws Exception {

        Task task =
                new Task();

        task.setId(
                rs.getInt("id")
        );

        task.setTitle(
                rs.getString("title")
        );

        task.setDescription(
                rs.getString("description")
        );

        task.setPriority(
                rs.getString("priority")
        );

        task.setStatus(
                rs.getString("status")
        );

        task.setDueDate(
                rs.getDate("due_date")
        );

        task.setCategoryId(
                rs.getInt("category_id")
        );

        task.setUserId(
                rs.getInt("user_id")
        );

        task.setFileName(
                rs.getString("task_file")
        );

        Timestamp createdAt =
                rs.getTimestamp("created_at");

        if (createdAt != null) {

            task.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        return task;
    }
}
