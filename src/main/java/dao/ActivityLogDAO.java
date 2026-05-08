package dao;

import model.ActivityLog;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogDAO {

    // =========================================
    // ADD ACTIVITY LOG
    // =========================================

    public boolean addActivityLog(ActivityLog log) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "INSERT INTO activity_logs(user_id, action, created_at) VALUES (?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, log.getUserId());

            ps.setString(2, log.getAction());

            ps.setTimestamp(
                    3,
                    java.sql.Timestamp.valueOf(
                            log.getCreatedAt()
                    )
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
    // GET ALL LOGS BY USER
    // =========================================

    public List<ActivityLog> getLogsByUser(int userId) {

        List<ActivityLog> logList =
                new ArrayList<>();

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT * FROM activity_logs WHERE user_id = ? ORDER BY created_at DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                ActivityLog log =
                        new ActivityLog();

                log.setId(rs.getInt("id"));

                log.setUserId(
                        rs.getInt("user_id")
                );

                log.setAction(
                        rs.getString("action")
                );

                log.setCreatedAt(
                        rs.getTimestamp("created_at")
                                .toLocalDateTime()
                );

                logList.add(log);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return logList;
    }

    // =========================================
    // DELETE LOG BY ID
    // =========================================

    public boolean deleteLog(int id) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM activity_logs WHERE id = ?";

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
    // DELETE ALL USER LOGS
    // =========================================

    public boolean deleteAllLogsByUser(int userId) {

        boolean status = false;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "DELETE FROM activity_logs WHERE user_id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, userId);

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
    // GET TOTAL LOG COUNT
    // =========================================

    public int getTotalLogs(int userId) {

        int count = 0;

        try {

            Connection conn =
                    DBConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) FROM activity_logs WHERE user_id = ?";

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
}