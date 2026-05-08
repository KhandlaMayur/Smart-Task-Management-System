-- =========================================
-- SMART TASK MANAGEMENT SYSTEM DATABASE
-- =========================================

CREATE DATABASE IF NOT EXISTS smart_task_management;

USE smart_task_management;

-- =========================================
-- DROP OLD TABLES
-- =========================================

DROP TABLE IF EXISTS activity_logs;
DROP TABLE IF EXISTS reminders;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- =========================================
-- USERS TABLE
-- =========================================

CREATE TABLE users (

                       id INT PRIMARY KEY AUTO_INCREMENT,

                       name VARCHAR(100) NOT NULL,

                       email VARCHAR(100) UNIQUE NOT NULL,

                       password VARCHAR(255) NOT NULL,

                       profile_image VARCHAR(255) DEFAULT 'default.png',

                       role VARCHAR(20) DEFAULT 'USER',

                       phone VARCHAR(20),

                       address TEXT,

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- CATEGORIES TABLE
-- =========================================

CREATE TABLE categories (

                            id INT PRIMARY KEY AUTO_INCREMENT,

                            category_name VARCHAR(100) NOT NULL UNIQUE,

                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- TASKS TABLE
-- =========================================

CREATE TABLE tasks (

                       id INT PRIMARY KEY AUTO_INCREMENT,

                       user_id INT NOT NULL,

                       category_id INT,

                       title VARCHAR(255) NOT NULL,

                       description TEXT,

                       priority ENUM(
        'Low',
        'Medium',
        'High'
    ) DEFAULT 'Medium',

                       status ENUM(
        'Pending',
        'In Progress',
        'Completed'
    ) DEFAULT 'Pending',

                       due_date DATE,

                       task_file VARCHAR(255),

                       progress INT DEFAULT 0,

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                           ON UPDATE CURRENT_TIMESTAMP,

                       FOREIGN KEY (user_id)
                           REFERENCES users(id)
                           ON DELETE CASCADE,

                       FOREIGN KEY (category_id)
                           REFERENCES categories(id)
                           ON DELETE SET NULL
);

-- =========================================
-- REMINDERS TABLE
-- =========================================

CREATE TABLE reminders (

                           id INT PRIMARY KEY AUTO_INCREMENT,

                           task_id INT NOT NULL,

                           reminder_time DATETIME NOT NULL,

                           message TEXT,

                           status ENUM(
        'Pending',
        'Completed'
    ) DEFAULT 'Pending',

                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                           FOREIGN KEY (task_id)
                               REFERENCES tasks(id)
                               ON DELETE CASCADE
);

-- =========================================
-- ACTIVITY LOG TABLE
-- =========================================

CREATE TABLE activity_logs (

                               id INT PRIMARY KEY AUTO_INCREMENT,

                               user_id INT,

                               activity VARCHAR(255),

                               ip_address VARCHAR(100),

                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE
);

-- =========================================
-- SAMPLE USERS
-- =========================================

INSERT INTO users (

    name,
    email,
    password,
    profile_image,
    role,
    phone,
    address

)

VALUES

    (
        'Mayur',
        'mayur@gmail.com',
        'Mayur@123',
        'mayur.jpg',
        'ADMIN',
        '9876543210',
        'Surendranagar, Gujarat'
    ),

    (
        'Rahul Patel',
        'rahul@gmail.com',
        'Rahul@123',
        'default.png',
        'USER',
        '9999999999',
        'Rajkot, Gujarat'
    ),

    (
        'Admin User',
        'admin@gmail.com',
        'Admin@123',
        'default.png',
        'ADMIN',
        '8888888888',
        'Ahmedabad, Gujarat'
    );

-- =========================================
-- SAMPLE CATEGORIES
-- =========================================

INSERT INTO categories (

    category_name

)

VALUES

    ('Study'),
    ('Office'),
    ('Project'),
    ('Meeting'),
    ('Personal'),
    ('Development');

-- =========================================
-- SAMPLE TASKS
-- =========================================

INSERT INTO tasks (

    user_id,
    category_id,
    title,
    description,
    priority,
    status,
    due_date,
    task_file,
    progress

)

VALUES

    (
        1,
        3,
        'Smart Task Management Project',
        'Develop Full Advanced Java Project Using JSP Servlet JDBC',
        'High',
        'In Progress',
        '2026-05-25',
        'project_report.pdf',
        70
    ),

    (
        1,
        1,
        'Complete Java Assignment',
        'Submit Servlet And JSP Assignment',
        'High',
        'Pending',
        '2026-05-15',
        'assignment.pdf',
        40
    ),

    (
        2,
        2,
        'Client Meeting Preparation',
        'Prepare Presentation For Client Meeting',
        'Medium',
        'Completed',
        '2026-05-10',
        'presentation.pptx',
        100
    ),

    (
        1,
        6,
        'Database Design',
        'Design MySQL Database Schema',
        'Medium',
        'Completed',
        '2026-05-12',
        'database_design.png',
        100
    ),

    (
        2,
        5,
        'Buy Office Accessories',
        'Purchase Keyboard And Mouse',
        'Low',
        'Pending',
        '2026-05-18',
        NULL,
        10
    );

-- =========================================
-- SAMPLE REMINDERS
-- =========================================

INSERT INTO reminders (

    task_id,
    reminder_time,
    message,
    status

)

VALUES

    (
        1,
        '2026-05-20 10:00:00',
        'Complete Remaining Project Modules',
        'Pending'
    ),

    (
        2,
        '2026-05-14 09:00:00',
        'Submit Assignment Tomorrow',
        'Pending'
    ),

    (
        3,
        '2026-05-09 08:30:00',
        'Prepare Client Meeting Presentation',
        'Completed'
    );

-- =========================================
-- SAMPLE ACTIVITY LOGS
-- =========================================

INSERT INTO activity_logs (

    user_id,
    activity,
    ip_address

)

VALUES

    (
        1,
        'Logged Into System',
        '192.168.1.1'
    ),

    (
        1,
        'Added New Task',
        '192.168.1.1'
    ),

    (
        2,
        'Completed Task',
        '192.168.1.2'
    ),

    (
        3,
        'Registered New User',
        '192.168.1.3'
    );

-- =========================================
-- INDEXES
-- =========================================

CREATE INDEX idx_user_email
    ON users(email);

CREATE INDEX idx_task_status
    ON tasks(status);

CREATE INDEX idx_task_priority
    ON tasks(priority);

CREATE INDEX idx_reminder_time
    ON reminders(reminder_time);

-- =========================================
-- VIEW ALL TABLES
-- =========================================

SHOW TABLES;

-- =========================================
-- DISPLAY SAMPLE DATA
-- =========================================

SELECT * FROM users;

SELECT * FROM categories;

SELECT * FROM tasks;

SELECT * FROM reminders;

SELECT * FROM activity_logs;