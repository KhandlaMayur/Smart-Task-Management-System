<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String contextPath =
            request.getContextPath();

    HttpSession userSession =
            request.getSession(false);

    if (userSession == null
            || userSession.getAttribute("userId") == null) {

        response.sendRedirect(contextPath + "/auth/login.jsp");

        return;
    }

    String userName =
            userSession.getAttribute("userName")
                    .toString();
    
    Integer totalTasks =
            (Integer) request.getAttribute("totalTasks");

    Integer completedTasks =
            (Integer) request.getAttribute("completedTasks");

    Integer pendingTasks =
            (Integer) request.getAttribute("pendingTasks");

    Integer inProgressTasks =
            (Integer) request.getAttribute("inProgressTasks");

    List<Task> recentTasks =
            (List<Task>) request.getAttribute("recentTasks");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Dashboard</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Dashboard CSS -->

    <link rel="stylesheet"
          href="<%= contextPath %>/css/dashboard.css">

    <!-- Global CSS -->

    <link rel="stylesheet"
          href="<%= contextPath %>/css/style.css">

</head>

<body>

<!-- Navbar -->

<jsp:include page="/components/navbar.jsp"/>

<div class="container-fluid">

    <div class="row">

        <!-- Sidebar -->

        <div class="col-md-2 p-0">

            <jsp:include page="/components/sidebar.jsp"/>

        </div>

        <!-- Main Content -->

        <div class="col-md-10 p-4">

            <!-- Header -->

            <div class="dashboard-header d-flex justify-content-between align-items-center">

                <div>

                    <h2>

                        Welcome,
                        <span class="text-primary">

                            <%= userName %>

                        </span>

                    </h2>

                    <p>
                        Manage Your Tasks Efficiently
                    </p>

                </div>

                <a href="<%= contextPath %>/task/add-task.jsp"
                   class="btn btn-primary">

                    <i class="fa-solid fa-plus"></i>

                    Add Task

                </a>

            </div>

            <!-- Message -->

            <jsp:include page="/components/message.jsp"/>

            <!-- Stats Cards -->

            <div class="row g-4 mb-4">

                <!-- Total Tasks -->

                <div class="col-md-3">

                    <a href="<%= contextPath %>/task?action=list"
                       class="stat-card-link">

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

                    </a>

                </div>

                <!-- Completed -->

                <div class="col-md-3">

                    <a href="<%= contextPath %>/task?action=list&status=Completed"
                       class="stat-card-link">

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

                    </a>

                </div>

                <!-- Pending -->

                <div class="col-md-3">

                    <a href="<%= contextPath %>/task?action=list&status=Pending"
                       class="stat-card-link">

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

                    </a>

                </div>

                <!-- In Progress -->

                <div class="col-md-3">

                    <a href="<%= contextPath %>/task?action=list&status=In%20Progress"
                       class="stat-card-link">

                    <div class="card stat-card overdue-task shadow-sm">

                        <div class="card-body">

                            <div class="icon-box">

                                <i class="fa-solid fa-triangle-exclamation"></i>

                            </div>

                            <h3>
                                <%= inProgressTasks %>
                            </h3>

                            <p>
                                In Progress Tasks
                            </p>

                        </div>

                    </div>

                    </a>

                </div>

            </div>

            <!-- Recent Tasks -->

            <div class="card task-table-card shadow-sm">

                <div class="card-header d-flex justify-content-between align-items-center">

                    <h5>

                        <i class="fa-solid fa-list text-primary"></i>

                        Recent Tasks

                    </h5>

                    <a href="<%= contextPath %>/task?action=list"
                       class="btn btn-sm btn-primary">

                        View All

                    </a>

                </div>

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead>

                            <tr>

                                <th>Title</th>

                                <th>Priority</th>

                                <th>Status</th>

                                <th>Due Date</th>

                                <th>Actions</th>

                            </tr>

                            </thead>

                            <tbody>

                            <%
                                if (recentTasks != null
                                        && !recentTasks.isEmpty()) {

                                    for (Task task : recentTasks) {
                            %>

                            <tr>

                                <!-- Title -->

                                <td>

                                    <%= task.getTitle() %>

                                </td>

                                <!-- Priority -->

                                <td>

                                    <%
                                        String priority =
                                                task.getPriority();

                                        if ("High".equalsIgnoreCase(priority)) {
                                    %>

                                    <span class="badge-priority-high">

                                        High

                                    </span>

                                    <%
                                    } else if ("Medium".equalsIgnoreCase(priority)) {
                                    %>

                                    <span class="badge-priority-medium">

                                        Medium

                                    </span>

                                    <%
                                    } else {
                                    %>

                                    <span class="badge-priority-low">

                                        Low

                                    </span>

                                    <%
                                        }
                                    %>

                                </td>

                                <!-- Status -->

                                <td>

                                    <%
                                        String status =
                                                task.getStatus();

                                        if ("Completed".equalsIgnoreCase(status)) {
                                    %>

                                    <span class="badge-status-completed">

                                        Completed

                                    </span>

                                    <%
                                    } else if ("In Progress".equalsIgnoreCase(status)) {
                                    %>

                                    <span class="badge-status-progress">

                                        In Progress

                                    </span>

                                    <%
                                    } else {
                                    %>

                                    <span class="badge-status-pending">

                                        Pending

                                    </span>

                                    <%
                                        }
                                    %>

                                </td>

                                <!-- Due Date -->

                                <td>

                                    <%= task.getDueDate() %>

                                </td>

                                <!-- Actions -->

                                <td>

                                    <!-- View -->

                                    <a href="<%= contextPath %>/task?action=view&id=<%= task.getId() %>"
                                       class="btn btn-sm btn-info action-btn">

                                        <i class="fa-solid fa-eye"></i>

                                    </a>

                                    <!-- Edit -->

                                    <a href="<%= contextPath %>/task/edit-task.jsp?id=<%= task.getId() %>"
                                       class="btn btn-sm btn-warning action-btn">

                                        <i class="fa-solid fa-pen"></i>

                                    </a>

                                    <%
                                        if ("Pending".equalsIgnoreCase(task.getStatus())) {
                                    %>

                                    <a href="<%= contextPath %>/reminder/add-reminder.jsp?taskId=<%= task.getId() %>"
                                       class="btn btn-sm btn-primary action-btn">

                                        <i class="fa-solid fa-bell"></i>

                                    </a>

                                    <%
                                        }
                                    %>

                                    <!-- Delete -->

                                    <a href="<%= contextPath %>/task?action=delete&id=<%= task.getId() %>"
                                       class="btn btn-sm btn-danger action-btn"
                                       onclick="return confirm('Are You Sure To Delete This Task?')">

                                        <i class="fa-solid fa-trash"></i>

                                    </a>

                                </td>

                            </tr>

                            <%
                                    }
                                } else {
                            %>

                            <tr>

                                <td colspan="5"
                                    class="text-center text-muted py-4">

                                    No Tasks Found

                                </td>

                            </tr>

                            <%
                                }
                            %>

                            </tbody>

                        </table>

                    </div>

                </div>

            </div>

            <!-- Footer -->

            <jsp:include page="/components/footer.jsp"/>

        </div>

    </div>

</div>

<!-- Bootstrap JS -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>
