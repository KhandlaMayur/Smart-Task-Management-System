-- =========================================
-- CREATE DATABASE
-- =========================================

CREATE DATABASE IF NOT EXISTS smart_task_management;

USE smart_task_management;

-- =========================================
-- USERS TABLE
-- =========================================

CREATE TABLE users (

                       id INT PRIMARY KEY AUTO_INCREMENT,

                       name VARCHAR(100) NOT NULL,

                       email VARCHAR(100) UNIQUE NOT NULL,

                       password VARCHAR(255) NOT NULL,

                       profile_image VARCHAR(255) DEFAULT 'default.png',

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================================
-- CATEGORIES TABLE
-- =========================================

CREATE TABLE categories (

                            id INT PRIMARY KEY AUTO_INCREMENT,

                            category_name VARCHAR(100) NOT NULL
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

                       priority VARCHAR(20),

                       status VARCHAR(30),

                       due_date DATE,

                       task_file VARCHAR(255),

                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

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

                           status VARCHAR(30),

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

                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE
);

-- =========================================
-- INSERT SAMPLE USERS
-- =========================================

INSERT INTO users (
    name,
    email,
    password,
    profile_image
)

VALUES

    (
        'Mayur',
        'mayur@gmail.com',
        'Mayur@123',
        'mayur.jpg'
    ),

    (
        'Admin User',
        'admin@gmail.com',
        'Admin@123',
        'default.png'
    );

-- =========================================
-- INSERT SAMPLE CATEGORIES
-- =========================================

INSERT INTO categories (
    category_name
)

VALUES

    ('Study'),
    ('Office'),
    ('Personal'),
    ('Project'),
    ('Meeting');

-- =========================================
-- INSERT SAMPLE TASKS
-- =========================================

INSERT INTO tasks (

    user_id,
    category_id,
    title,
    description,
    priority,
    status,
    due_date,
    task_file

)

VALUES

    (
        1,
        1,
        'Complete Java Assignment',
        'Finish Advanced Java Servlet Project',
        'High',
        'In Progress',
        '2026-05-15',
        'assignment.pdf'
    ),

    (
        1,
        2,
        'Prepare Presentation',
        'Prepare Client Presentation Slides',
        'Medium',
        'Pending',
        '2026-05-20',
        'presentation.pptx'
    ),

    (
        1,
        4,
        'Smart Task Management System',
        'Develop Full Stack Task Management Project',
        'High',
        'Completed',
        '2026-05-10',
        'project_report.pdf'
    ),

    (
        2,
        3,
        'Buy Groceries',
        'Purchase Weekly Grocery Items',
        'Low',
        'Pending',
        '2026-05-12',
        NULL
    );

-- =========================================
-- INSERT SAMPLE REMINDERS
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
        '2026-05-14 09:00:00',
        'Submit Java Assignment Tomorrow',
        'Pending'
    ),

    (
        2,
        '2026-05-18 10:30:00',
        'Prepare Presentation Before Meeting',
        'Pending'
    ),

    (
        3,
        '2026-05-09 08:00:00',
        'Complete Final Project Documentation',
        'Completed'
    );

-- =========================================
-- INSERT SAMPLE ACTIVITY LOGS
-- =========================================

INSERT INTO activity_logs (

    user_id,
    activity

)

VALUES

    (
        1,
        'Logged Into System'
    ),

    (
        1,
        'Added New Task'
    ),

    (
        1,
        'Updated Task Status'
    ),

    (
        2,
        'Registered New Account'
    );

-- =========================================
-- SHOW TABLES
-- =========================================

SHOW TABLES;

-- =========================================
-- DISPLAY DATA
-- =========================================

SELECT * FROM users;

SELECT * FROM categories;

SELECT * FROM tasks;

SELECT * FROM reminders;

SELECT * FROM activity_logs;