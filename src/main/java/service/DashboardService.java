package service;

import dao.TaskDAO;

import java.util.HashMap;
import java.util.Map;

public class DashboardService {

    private TaskDAO taskDAO;

    // =========================================
    // CONSTRUCTOR
    // =========================================

    public DashboardService() {

        taskDAO = new TaskDAO();
    }

    // =========================================
    // GET DASHBOARD STATISTICS
    // =========================================

    public Map<String, Object> getDashboardStatistics(int userId) {

        Map<String, Object> dashboardData =
                new HashMap<>();

        try {

            // =========================================
            // FETCH TASK COUNTS
            // =========================================

            int totalTasks =
                    taskDAO.getTotalTasks(userId);

            int completedTasks =
                    taskDAO.getCompletedTasks(userId);

            int pendingTasks =
                    taskDAO.getPendingTasks(userId);

            int inProgressTasks =
                    taskDAO.getInProgressTasks(userId);

            int overdueTasks =
                    taskDAO.getOverdueTasks(userId);

            int highPriorityTasks =
                    taskDAO.getHighPriorityTasks(userId);

            // =========================================
            // CALCULATE PROGRESS
            // =========================================

            double progressPercentage = 0;

            if (totalTasks > 0) {

                progressPercentage =
                        ((double) completedTasks / totalTasks) * 100;
            }

            // =========================================
            // STORE DATA
            // =========================================

            dashboardData.put(
                    "totalTasks",
                    totalTasks
            );

            dashboardData.put(
                    "completedTasks",
                    completedTasks
            );

            dashboardData.put(
                    "pendingTasks",
                    pendingTasks
            );

            dashboardData.put(
                    "inProgressTasks",
                    inProgressTasks
            );

            dashboardData.put(
                    "overdueTasks",
                    overdueTasks
            );

            dashboardData.put(
                    "highPriorityTasks",
                    highPriorityTasks
            );

            dashboardData.put(
                    "progressPercentage",
                    progressPercentage
            );

            // =========================================
            // RECENT TASKS
            // =========================================

            dashboardData.put(
                    "recentTasks",
                    taskDAO.getRecentTasks(userId)
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return dashboardData;
    }

    // =========================================
    // GET PRODUCTIVITY MESSAGE
    // =========================================

    public String getProductivityMessage(int userId) {

        int totalTasks =
                taskDAO.getTotalTasks(userId);

        int completedTasks =
                taskDAO.getCompletedTasks(userId);

        if (totalTasks == 0) {

            return "Start Adding Tasks To Track Productivity";
        }

        double percentage =
                ((double) completedTasks / totalTasks) * 100;

        if (percentage >= 90) {

            return "Excellent Productivity";

        } else if (percentage >= 70) {

            return "Good Productivity";

        } else if (percentage >= 50) {

            return "Average Productivity";

        } else {

            return "Need Improvement";
        }
    }

    // =========================================
    // GET TASK COMPLETION RATE
    // =========================================

    public double getCompletionRate(int userId) {

        int totalTasks =
                taskDAO.getTotalTasks(userId);

        int completedTasks =
                taskDAO.getCompletedTasks(userId);

        if (totalTasks == 0) {

            return 0;
        }

        return ((double) completedTasks / totalTasks) * 100;
    }
}