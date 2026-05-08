<%@ page import="dao.TaskDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession userSession =
            request.getSession(false);

    if (userSession == null
            || userSession.getAttribute("userId") == null) {

        response.sendRedirect("../auth/login.jsp");

        return;
    }

    int userId =
            Integer.parseInt(
                    userSession.getAttribute("userId")
                            .toString()
            );

    TaskDAO taskDAO =
            new TaskDAO();

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
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Analytics Dashboard</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Chart JS -->

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <!-- Custom CSS -->

    <link rel="stylesheet"
          href="../css/dashboard.css">

</head>

<body class="bg-light">

<!-- Navbar -->

<jsp:include page="../components/navbar.jsp"/>

<div class="container-fluid">

    <div class="row">

        <!-- Sidebar -->

        <div class="col-md-2 p-0">

            <jsp:include page="../components/sidebar.jsp"/>

        </div>

        <!-- Main Content -->

        <div class="col-md-10 p-4">

            <!-- Header -->

            <div class="dashboard-header mb-4">

                <h2>

                    <i class="fa-solid fa-chart-line text-primary"></i>

                    Task Analytics

                </h2>

                <p>
                    Visual Overview Of Your Tasks
                </p>

            </div>

            <!-- Stats Cards -->

            <div class="row g-4 mb-4">

                <!-- Total -->

                <div class="col-md-4">

                    <div class="card stat-card total-task shadow-sm">

                        <div class="card-body">

                            <div class="icon-box">

                                <i class="fa-solid fa-list-check"></i>

                            </div>

                            <h3>
                                <%= totalTasks %>
                            </h3>

                            <p>
                                Total Tasks
                            </p>

                        </div>

                    </div>

                </div>

                <!-- Completed -->

                <div class="col-md-4">

                    <div class="card stat-card completed-task shadow-sm">

                        <div class="card-body">

                            <div class="icon-box">

                                <i class="fa-solid fa-circle-check"></i>

                            </div>

                            <h3>
                                <%= completedTasks %>
                            </h3>

                            <p>
                                Completed Tasks
                            </p>

                        </div>

                    </div>

                </div>

                <!-- Pending -->

                <div class="col-md-4">

                    <div class="card stat-card pending-task shadow-sm">

                        <div class="card-body">

                            <div class="icon-box">

                                <i class="fa-solid fa-clock"></i>

                            </div>

                            <h3>
                                <%= pendingTasks %>
                            </h3>

                            <p>
                                Pending Tasks
                            </p>

                        </div>

                    </div>

                </div>

            </div>

            <!-- Charts -->

            <div class="row g-4">

                <!-- Pie Chart -->

                <div class="col-md-6">

                    <div class="card chart-card shadow-sm">

                        <div class="card-header">

                            <h5>

                                <i class="fa-solid fa-chart-pie text-primary"></i>

                                Task Status Distribution

                            </h5>

                        </div>

                        <div class="card-body">

                            <canvas id="taskPieChart"></canvas>

                        </div>

                    </div>

                </div>

                <!-- Bar Chart -->

                <div class="col-md-6">

                    <div class="card chart-card shadow-sm">

                        <div class="card-header">

                            <h5>

                                <i class="fa-solid fa-chart-column text-success"></i>

                                Task Overview

                            </h5>

                        </div>

                        <div class="card-body">

                            <canvas id="taskBarChart"></canvas>

                        </div>

                    </div>

                </div>

            </div>

            <!-- Extra Analytics -->

            <div class="row g-4 mt-3">

                <!-- Overdue -->

                <div class="col-md-6">

                    <div class="card shadow-sm border-0 rounded-4">

                        <div class="card-body">

                            <h5>

                                <i class="fa-solid fa-triangle-exclamation text-danger"></i>

                                Overdue Tasks

                            </h5>

                            <h2 class="text-danger mt-3">

                                <%= overdueTasks %>

                            </h2>

                        </div>

                    </div>

                </div>

                <!-- High Priority -->

                <div class="col-md-6">

                    <div class="card shadow-sm border-0 rounded-4">

                        <div class="card-body">

                            <h5>

                                <i class="fa-solid fa-fire text-warning"></i>

                                High Priority Tasks

                            </h5>

                            <h2 class="text-warning mt-3">

                                <%= highPriorityTasks %>

                            </h2>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

<!-- Charts Script -->

<script>

    // =========================================
    // PIE CHART
    // =========================================

    const pieCtx =
        document.getElementById('taskPieChart');

    new Chart(pieCtx, {

        type: 'pie',

        data: {

            labels: [
                'Completed',
                'Pending',
                'In Progress'
            ],

            datasets: [{

                data: [
                    <%= completedTasks %>,
                    <%= pendingTasks %>,
                    <%= inProgressTasks %>
                ],

                backgroundColor: [
                    '#198754',
                    '#ffc107',
                    '#0d6efd'
                ]

            }]
        }
    });

    // =========================================
    // BAR CHART
    // =========================================

    const barCtx =
        document.getElementById('taskBarChart');

    new Chart(barCtx, {

        type: 'bar',

        data: {

            labels: [
                'Total',
                'Completed',
                'Pending',
                'In Progress',
                'Overdue'
            ],

            datasets: [{

                label: 'Tasks',

                data: [
                    <%= totalTasks %>,
                    <%= completedTasks %>,
                    <%= pendingTasks %>,
                    <%= inProgressTasks %>,
                    <%= overdueTasks %>
                ],

                backgroundColor: [
                    '#0d6efd',
                    '#198754',
                    '#ffc107',
                    '#6610f2',
                    '#dc3545'
                ]

            }]
        },

        options: {

            responsive: true,

            plugins: {

                legend: {

                    display: false
                }
            }
        }
    });

</script>

</body>
</html>