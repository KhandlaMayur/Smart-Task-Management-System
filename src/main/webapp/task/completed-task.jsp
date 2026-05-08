<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
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

    List<Task> completedTasks =
            taskDAO.getCompletedTaskList(userId);
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Completed Tasks</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->

    <link rel="stylesheet"
          href="../css/style.css">

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

            <!-- Page Header -->

            <div class="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <h2 class="fw-bold">

                        <i class="fa-solid fa-circle-check text-success"></i>

                        Completed Tasks

                    </h2>

                    <p class="text-muted">

                        View All Successfully Completed Tasks

                    </p>

                </div>

                <a href="add-task.jsp"
                   class="btn btn-primary">

                    <i class="fa-solid fa-plus"></i>

                    Add Task

                </a>

            </div>

            <!-- Messages -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Completed Task Table -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead class="table-success">

                            <tr>

                                <th>ID</th>

                                <th>Title</th>

                                <th>Description</th>

                                <th>Priority</th>

                                <th>Due Date</th>

                                <th>Status</th>

                                <th class="text-center">

                                    Actions

                                </th>

                            </tr>

                            </thead>

                            <tbody>

                            <%
                                if (completedTasks != null
                                        && !completedTasks.isEmpty()) {

                                    for (Task task : completedTasks) {
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

                                <!-- Due Date -->

                                <td>

                                    <%= task.getDueDate() %>

                                </td>

                                <!-- Status -->

                                <td>

                                    <span class="badge bg-success">

                                        <i class="fa-solid fa-check"></i>

                                        Completed

                                    </span>

                                </td>

                                <!-- Actions -->

                                <td class="text-center">

                                    <!-- View -->

                                    <a href="../task?action=view&id=<%= task.getId() %>"
                                       class="btn btn-info btn-sm">

                                        <i class="fa-solid fa-eye"></i>

                                    </a>

                                    <!-- Delete -->

                                    <a href="../deleteTask?id=<%= task.getId() %>"
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

                                    No Completed Tasks Found

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

            <jsp:include page="../components/footer.jsp"/>

        </div>

    </div>

</div>

<!-- Bootstrap -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>