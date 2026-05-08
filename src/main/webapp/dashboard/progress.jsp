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

    double completionPercentage = 0;

    if (totalTasks > 0) {

        completionPercentage =
                ((double) completedTasks / totalTasks) * 100;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Task Progress</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Custom CSS -->

    <link rel="stylesheet"
          href="../css/dashboard.css">

    <link rel="stylesheet"
          href="../css/style.css">

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

            <!-- Page Header -->

            <div class="dashboard-header mb-4">

                <h2>

                    <i class="fa-solid fa-chart-simple text-primary"></i>

                    Task Progress

                </h2>

                <p>
                    Track Your Productivity And Task Completion
                </p>

            </div>

            <!-- Progress Overview -->

            <div class="card shadow-sm border-0 rounded-4 mb-4">

                <div class="card-body p-4">

                    <div class="d-flex justify-content-between align-items-center mb-3">

                        <h4 class="fw-bold">

                            Overall Progress

                        </h4>

                        <span class="badge bg-primary fs-6">

                            <%= String.format("%.2f", completionPercentage) %>%

                        </span>

                    </div>

                    <!-- Progress Bar -->

                    <div class="progress"
                         style="height: 30px;">

                        <div class="progress-bar progress-bar-striped progress-bar-animated bg-success"
                             role="progressbar"
                             style="width: <%= completionPercentage %>%">

                            <%= String.format("%.2f", completionPercentage) %>%

                        </div>

                    </div>

                </div>

            </div>

            <!-- Statistics Cards -->

            <div class="row g-4 mb-4">

                <!-- Total Tasks -->

                <div class="col-md-3">

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

                <div class="col-md-3">

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

                <div class="col-md-3">

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

                <!-- Overdue -->

                <div class="col-md-3">

                    <div class="card stat-card overdue-task shadow-sm">

                        <div class="card-body">

                            <div class="icon-box">

                                <i class="fa-solid fa-triangle-exclamation"></i>

                            </div>

                            <h3>

                                <%= overdueTasks %>

                            </h3>

                            <p>
                                Overdue Tasks
                            </p>

                        </div>

                    </div>

                </div>

            </div>

            <!-- Progress Details -->

            <div class="card shadow-sm border-0 rounded-4">

                <div class="card-header bg-white">

                    <h5 class="fw-bold mb-0">

                        <i class="fa-solid fa-chart-pie text-success"></i>

                        Progress Details

                    </h5>

                </div>

                <div class="card-body p-4">

                    <!-- Completed Progress -->

                    <div class="mb-4">

                        <div class="d-flex justify-content-between">

                            <span class="fw-semibold">
                                Completed Tasks
                            </span>

                            <span>
                                <%= completedTasks %> / <%= totalTasks %>
                            </span>

                        </div>

                        <div class="progress mt-2"
                             style="height: 20px;">

                            <div class="progress-bar bg-success"
                                 style="width: <%= completionPercentage %>%">
                            </div>

                        </div>

                    </div>

                    <!-- Pending Progress -->

                    <div class="mb-4">

                        <div class="d-flex justify-content-between">

                            <span class="fw-semibold">
                                Pending Tasks
                            </span>

                            <span>
                                <%= pendingTasks %>
                            </span>

                        </div>

                        <div class="progress mt-2"
                             style="height: 20px;">

                            <%
                                double pendingPercentage = 0;

                                if (totalTasks > 0) {

                                    pendingPercentage =
                                            ((double) pendingTasks / totalTasks) * 100;
                                }
                            %>

                            <div class="progress-bar bg-warning"
                                 style="width: <%= pendingPercentage %>%">
                            </div>

                        </div>

                    </div>

                    <!-- In Progress -->

                    <div class="mb-4">

                        <div class="d-flex justify-content-between">

                            <span class="fw-semibold">
                                In Progress Tasks
                            </span>

                            <span>
                                <%= inProgressTasks %>
                            </span>

                        </div>

                        <div class="progress mt-2"
                             style="height: 20px;">

                            <%
                                double progressPercentage = 0;

                                if (totalTasks > 0) {

                                    progressPercentage =
                                            ((double) inProgressTasks / totalTasks) * 100;
                                }
                            %>

                            <div class="progress-bar bg-primary"
                                 style="width: <%= progressPercentage %>%">
                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <!-- Footer -->

            <jsp:include page="../components/footer.jsp"/>

        </div>

    </div>

</div>

<!-- Bootstrap JS -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>