<%@ page import="model.Task" %>
<%@ page import="dao.TaskDAO" %>

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

    String taskIdParam =
            request.getParameter("id");

    if (taskIdParam == null) {

        response.sendRedirect(
                contextPath + "/task?action=list&error=Invalid Task ID"
        );

        return;
    }

    int taskId =
            Integer.parseInt(taskIdParam);

    TaskDAO taskDAO =
            new TaskDAO();

    Task task =
            taskDAO.getTaskById(taskId);

    if (task == null) {

        response.sendRedirect(
                contextPath + "/task?action=list&error=Task Not Found"
        );

        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Task Details</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->

    <link rel="stylesheet"
          href="<%= contextPath %>/css/style.css">

    <link rel="stylesheet"
          href="<%= contextPath %>/css/dashboard.css">

</head>

<body class="bg-light">

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

            <div class="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <h2 class="fw-bold">

                        <i class="fa-solid fa-eye text-primary"></i>

                        Task Details

                    </h2>

                    <p class="text-muted">

                        Detailed Information About Selected Task

                    </p>

                </div>

                <div>

                    <%
                        if ("Pending".equalsIgnoreCase(task.getStatus())) {
                    %>

                    <a href="<%= contextPath %>/reminder/add-reminder.jsp?taskId=<%= task.getId() %>"
                       class="btn btn-primary">

                        <i class="fa-solid fa-bell"></i>

                        Set Reminder

                    </a>

                    <%
                        }
                    %>

                    <a href="<%= contextPath %>/task/edit-task.jsp?id=<%= task.getId() %>"
                       class="btn btn-warning text-white">

                        <i class="fa-solid fa-pen"></i>

                        Edit

                    </a>

                    <a href="<%= contextPath %>/task?action=list"
                       class="btn btn-secondary">

                        <i class="fa-solid fa-arrow-left"></i>

                        Back

                    </a>

                </div>

            </div>

            <!-- Messages -->

            <jsp:include page="/components/message.jsp"/>

            <!-- Task Detail Card -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <!-- Task Title -->

                    <div class="mb-4">

                        <h2 class="fw-bold text-primary">

                            <%= task.getTitle() %>

                        </h2>

                    </div>

                    <!-- Task Information -->

                    <div class="row g-4">

                        <!-- Description -->

                        <div class="col-md-12">

                            <div class="card border-0 bg-light">

                                <div class="card-body">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-file-lines text-primary"></i>

                                        Description

                                    </h5>

                                    <p class="mt-3">

                                        <%= task.getDescription() %>

                                    </p>

                                </div>

                            </div>

                        </div>

                        <!-- Priority -->

                        <div class="col-md-4">

                            <div class="card border-0 bg-light">

                                <div class="card-body text-center">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-flag text-danger"></i>

                                        Priority

                                    </h5>

                                    <div class="mt-3">

                                        <%
                                            String priority =
                                                    task.getPriority();

                                            if ("High".equalsIgnoreCase(priority)) {
                                        %>

                                        <span class="badge bg-danger fs-6">

                                            High

                                        </span>

                                        <%
                                        } else if ("Medium".equalsIgnoreCase(priority)) {
                                        %>

                                        <span class="badge bg-warning text-dark fs-6">

                                            Medium

                                        </span>

                                        <%
                                        } else {
                                        %>

                                        <span class="badge bg-success fs-6">

                                            Low

                                        </span>

                                        <%
                                            }
                                        %>

                                    </div>

                                </div>

                            </div>

                        </div>

                        <!-- Status -->

                        <div class="col-md-4">

                            <div class="card border-0 bg-light">

                                <div class="card-body text-center">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-circle-info text-primary"></i>

                                        Status

                                    </h5>

                                    <div class="mt-3">

                                        <%
                                            String status =
                                                    task.getStatus();

                                            if ("Completed".equalsIgnoreCase(status)) {
                                        %>

                                        <span class="badge bg-success fs-6">

                                            Completed

                                        </span>

                                        <%
                                        } else if ("In Progress".equalsIgnoreCase(status)) {
                                        %>

                                        <span class="badge bg-primary fs-6">

                                            In Progress

                                        </span>

                                        <%
                                        } else {
                                        %>

                                        <span class="badge bg-warning text-dark fs-6">

                                            Pending

                                        </span>

                                        <%
                                            }
                                        %>

                                    </div>

                                </div>

                            </div>

                        </div>

                        <!-- Due Date -->

                        <div class="col-md-4">

                            <div class="card border-0 bg-light">

                                <div class="card-body text-center">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-calendar-days text-success"></i>

                                        Due Date

                                    </h5>

                                    <p class="mt-3 fw-semibold">

                                        <%= task.getDueDate() %>

                                    </p>

                                </div>

                            </div>

                        </div>

                        <!-- Category -->

                        <div class="col-md-6">

                            <div class="card border-0 bg-light">

                                <div class="card-body">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-layer-group text-info"></i>

                                        Category ID

                                    </h5>

                                    <p class="mt-3">

                                        <%= task.getCategoryId() %>

                                    </p>

                                </div>

                            </div>

                        </div>

                        <!-- Created Date -->

                        <div class="col-md-6">

                            <div class="card border-0 bg-light">

                                <div class="card-body">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-clock text-secondary"></i>

                                        Created Date

                                    </h5>

                                    <p class="mt-3">

                                        <%= task.getCreatedAt() %>

                                    </p>

                                </div>

                            </div>

                        </div>

                        <!-- Task File -->

                        <div class="col-md-12">

                            <div class="card border-0 bg-light">

                                <div class="card-body">

                                    <h5 class="fw-bold">

                                        <i class="fa-solid fa-file-arrow-down text-primary"></i>

                                        Attached File

                                    </h5>

                                    <div class="mt-3">

                                        <%
                                            if (task.getTaskFile() != null
                                                    && !task.getTaskFile().isEmpty()) {
                                        %>

                                    <a href="<%= contextPath %>/uploads/task-files/<%= task.getFileName() %>"
                                           target="_blank"
                                           class="btn btn-outline-primary">

                                            <i class="fa-solid fa-download"></i>

                                            Download File

                                        </a>

                                        <%
                                        } else {
                                        %>

                                        <span class="text-muted">

                                            No File Uploaded

                                        </span>

                                        <%
                                            }
                                        %>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                    <!-- Action Buttons -->

                    <div class="mt-5 d-flex gap-3">

                    <%
                        if ("Pending".equalsIgnoreCase(task.getStatus())) {
                    %>

                    <a href="<%= contextPath %>/reminder/add-reminder.jsp?taskId=<%= task.getId() %>"
                           class="btn btn-primary">

                            <i class="fa-solid fa-bell"></i>

                            Set Reminder

                        </a>

                    <%
                        }
                    %>

                    <a href="<%= contextPath %>/task/edit-task.jsp?id=<%= task.getId() %>"
                           class="btn btn-warning text-white">

                            <i class="fa-solid fa-pen"></i>

                            Edit Task

                        </a>

                    <a href="<%= contextPath %>/deleteTask?id=<%= task.getId() %>"
                           class="btn btn-danger"
                           onclick="return confirm('Are You Sure To Delete This Task?')">

                            <i class="fa-solid fa-trash"></i>

                            Delete Task

                        </a>

                    </div>

                </div>

            </div>

            <!-- Footer -->

            <jsp:include page="/components/footer.jsp"/>

        </div>

    </div>

</div>

<!-- Bootstrap -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>
