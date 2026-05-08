package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // =========================================
    // DATABASE CONFIGURATION
    // =========================================

    private static final String URL =
            "jdbc:mysql://localhost:3306/smart_task_management";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "";

    // =========================================
    // CONNECTION OBJECT
    // =========================================

    private static Connection connection;

    // =========================================
    // GET DATABASE CONNECTION
    // =========================================

    public static Connection getConnection() {

        try {

            // LOAD MYSQL DRIVER

            Class.forName(
                    "com.mysql.cj.jdbc.Driver"
            );

            // CREATE CONNECTION

            if (connection == null
                    || connection.isClosed()) {

                connection =
                        DriverManager.getConnection(
                                URL,
                                USERNAME,
                                PASSWORD
                        );

                System.out.println(
                        "Database Connected Successfully"
                );
            }

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "MySQL JDBC Driver Not Found"
            );

            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println(
                    "Database Connection Failed"
            );

            e.printStackTrace();
        }

        return connection;
    }

    // =========================================
    // CLOSE DATABASE CONNECTION
    // =========================================

    public static void closeConnection() {

        try {

            if (connection != null
                    && !connection.isClosed()) {

                connection.close();

                System.out.println(
                        "Database Connection Closed"
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =========================================
    // TEST DATABASE CONNECTION
    // =========================================

    public static void main(String[] args) {

        Connection conn =
                DBConnection.getConnection();

        if (conn != null) {

            System.out.println(
                    "Connection Test Successful"
            );

        } else {

            System.out.println(
                    "Connection Test Failed"
            );
        }
    }
}