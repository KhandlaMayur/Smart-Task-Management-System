package service;

import dao.TaskDAO;
import model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private TaskDAO taskDAO;

    // =========================================
    // CONSTRUCTOR
    // =========================================

    public TaskService() {

        taskDAO = new TaskDAO();
    }

    // =========================================
    // ADD TASK
    // =========================================

    public boolean addTask(Task task) {

        // AUTO PRIORITY LOGIC

        if (task.getDueDate() != null) {

            LocalDate today =
                    LocalDate.now();

            LocalDate dueDate =
                    task.getDueDate().toLocalDate();

            long days =
                    java.time.temporal.ChronoUnit.DAYS
                            .between(today, dueDate);

            if (days <= 2) {

                task.setPriority("High");
            }
        }

        return taskDAO.addTask(task);
    }

    // =========================================
    // UPDATE TASK
    // =========================================

    public boolean updateTask(Task task) {

        return taskDAO.updateTask(task);
    }

    // =========================================
    // DELETE TASK
    // =========================================

    public boolean deleteTask(int id) {

        return taskDAO.deleteTask(id);
    }

    // =========================================
    // GET TASK BY ID
    // =========================================

    public Task getTaskById(int id) {

        return taskDAO.getTaskById(id);
    }

    // =========================================
    // GET ALL TASKS
    // =========================================

    public List<Task> getAllTasks(int userId) {

        return taskDAO.getAllTasks(userId);
    }

    // =========================================
    // SEARCH TASKS
    // =========================================

    public List<Task> searchTasks(int userId,
                                  String keyword,
                                  String priority,
                                  String status,
                                  int categoryId) {

        return taskDAO.searchTasks(
                userId,
                keyword,
                priority,
                status,
                categoryId
        );
    }

    // =========================================
    // MARK TASK COMPLETED
    // =========================================

    public boolean markTaskCompleted(int id) {

        return taskDAO.markTaskCompleted(id);
    }

    // =========================================
    // GET OVERDUE TASKS
    // =========================================

    public List<Task> getOverdueTasks(int userId) {

        List<Task> overdueTasks =
                new ArrayList<>();

        try {

            List<Task> taskList =
                    taskDAO.getAllTasks(userId);

            LocalDate today =
                    LocalDate.now();

            for (Task task : taskList) {

                if (task.getDueDate() != null
                        && task.getStatus() != null) {

                    LocalDate dueDate =
                            task.getDueDate().toLocalDate();

                    boolean isOverdue =
                            dueDate.isBefore(today);

                    boolean isNotCompleted =
                            !task.getStatus()
                                    .equalsIgnoreCase("Completed");

                    if (isOverdue && isNotCompleted) {

                        overdueTasks.add(task);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return overdueTasks;
    }

    // =========================================
    // GET TODAY TASKS
    // =========================================

    public List<Task> getTodayTasks(int userId) {

        List<Task> todayTasks =
                new ArrayList<>();

        try {

            List<Task> taskList =
                    taskDAO.getAllTasks(userId);

            LocalDate today =
                    LocalDate.now();

            for (Task task : taskList) {

                if (task.getDueDate() != null) {

                    LocalDate dueDate =
                            task.getDueDate().toLocalDate();

                    if (dueDate.isEqual(today)) {

                        todayTasks.add(task);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return todayTasks;
    }

    // =========================================
    // GET HIGH PRIORITY TASKS
    // =========================================

    public List<Task> getHighPriorityTasks(int userId) {

        List<Task> highPriorityTasks =
                new ArrayList<>();

        try {

            List<Task> taskList =
                    taskDAO.getAllTasks(userId);

            for (Task task : taskList) {

                if (task.getPriority() != null
                        && task.getPriority()
                        .equalsIgnoreCase("High")) {

                    highPriorityTasks.add(task);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return highPriorityTasks;
    }

    // =========================================
    // GET TASK COMPLETION PERCENTAGE
    // =========================================

    public double getTaskCompletionPercentage(int userId) {

        int totalTasks =
                taskDAO.getTotalTasks(userId);

        int completedTasks =
                taskDAO.getCompletedTasks(userId);

        if (totalTasks == 0) {

            return 0;
        }

        return ((double) completedTasks / totalTasks) * 100;
    }

    // =========================================
    // GET PENDING TASK COUNT
    // =========================================

    public int getPendingTaskCount(int userId) {

        return taskDAO.getPendingTasks(userId);
    }

    // =========================================
    // GET COMPLETED TASK COUNT
    // =========================================

    public int getCompletedTaskCount(int userId) {

        return taskDAO.getCompletedTasks(userId);
    }
}