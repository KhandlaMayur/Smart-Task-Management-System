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

    String keyword =
            (String) request.getAttribute("keyword");

    if (keyword == null) {
        keyword = request.getParameter("keyword");
    }

    List<Task> taskList =
            (List<Task>) request.getAttribute("taskList");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Search Tasks</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->

    <link rel="stylesheet"
          href="<%= contextPath %>/css/style.css">

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

                        <i class="fa-solid fa-magnifying-glass text-primary"></i>

                        Search Tasks

                    </h2>

                    <p class="text-muted">

                        Search Tasks By Title, Description Or Status

                    </p>

                </div>

            </div>

            <!-- Messages -->

            <jsp:include page="/components/message.jsp"/>

            <!-- Search Card -->

            <div class="card shadow border-0 rounded-4 mb-4">

                <div class="card-body p-4">

                    <form action="<%= contextPath %>/searchTask"
                          method="get">

                        <div class="row g-3">

                            <!-- Search Input -->

                            <div class="col-md-10">

                                <div class="input-group">

                                    <span class="input-group-text">

                                        <i class="fa-solid fa-search"></i>

                                    </span>

                                    <input type="text"
                                           name="keyword"
                                           class="form-control"
                                           placeholder="Enter Task Title, Description Or Status"
                                           value="<%= keyword != null ? keyword : "" %>"
                                           required>

                                </div>

                            </div>

                            <!-- Search Button -->

                            <div class="col-md-2 d-grid">

                                <button type="submit"
                                        class="btn btn-primary">

                                    <i class="fa-solid fa-magnifying-glass"></i>

                                    Search

                                </button>

                            </div>

                        </div>

                    </form>

                </div>

            </div>

            <!-- Search Result -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-header bg-white">

                    <h5 class="fw-bold mb-0">

                        <i class="fa-solid fa-list text-success"></i>

                        Search Results

                    </h5>

                </div>

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead class="table-dark">

                            <tr>

                                <th>ID</th>

                                <th>Title</th>

                                <th>Description</th>

                                <th>Priority</th>

                                <th>Status</th>

                                <th>Due Date</th>

                                <th class="text-center">

                                    Actions

                                </th>

                            </tr>

                            </thead>

                            <tbody>

                            <%
                                if (taskList != null
                                        && !taskList.isEmpty()) {

                                    for (Task task : taskList) {
                            %>

                            <tr>

                                <!-- ID -->

                                <td>

                                    <%= task.getId() %>

                                </td>

                                <!-- Title -->

                                <td>

                                    <%= task.getTitle() %>

                                </td>

                                <!-- Description -->

                                <td>

                                    <%= task.getDescription() %>

                                </td>

                                <!-- Priority -->

                                <td>

                                    <%
                                        String priority =
                                                task.getPriority();

                                        if ("High".equalsIgnoreCase(priority)) {
                                    %>

                                    <span class="badge bg-danger">

                                        High

                                    </span>

                                    <%
                                    } else if ("Medium".equalsIgnoreCase(priority)) {
                                    %>

                                    <span class="badge bg-warning text-dark">

                                        Medium

                                    </span>

                                    <%
                                    } else {
                                    %>

                                    <span class="badge bg-success">

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

                                    <span class="badge bg-success">

                                        Completed

                                    </span>

                                    <%
                                    } else if ("In Progress".equalsIgnoreCase(status)) {
                                    %>

                                    <span class="badge bg-primary">

                                        In Progress

                                    </span>

                                    <%
                                    } else {
                                    %>

                                    <span class="badge bg-warning text-dark">

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

                                <td class="text-center">

                                    <!-- View -->

                                    <a href="<%= contextPath %>/task?action=view&id=<%= task.getId() %>"
                                       class="btn btn-info btn-sm">

                                        <i class="fa-solid fa-eye"></i>

                                    </a>

                                    <!-- Edit -->

                                    <a href="<%= contextPath %>/task/edit-task.jsp?id=<%= task.getId() %>"
                                       class="btn btn-warning btn-sm">

                                        <i class="fa-solid fa-pen"></i>

                                    </a>

                                    <%
                                        if ("Pending".equalsIgnoreCase(task.getStatus())) {
                                    %>

                                    <a href="<%= contextPath %>/reminder/add-reminder.jsp?taskId=<%= task.getId() %>"
                                       class="btn btn-primary btn-sm">

                                        <i class="fa-solid fa-bell"></i>

                                    </a>

                                    <%
                                        }
                                    %>

                                    <!-- Delete -->

                                    <a href="<%= contextPath %>/deleteTask?id=<%= task.getId() %>"
                                       class="btn btn-danger btn-sm"
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

                                <td colspan="7"
                                    class="text-center text-muted py-4">

                                    <%
                                        if (keyword != null) {
                                    %>

                                    No Tasks Found

                                    <%
                                        } else {
                                    %>

                                    Search Tasks Using Keyword

                                    <%
                                        }
                                    %>

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

<!-- Bootstrap -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>
