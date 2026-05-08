<%@ page import="java.util.List" %>
<%@ page import="model.Reminder" %>
<%@ page import="model.Task" %>
<%@ page import="dao.ReminderDAO" %>
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

    ReminderDAO reminderDAO =
            new ReminderDAO();

    TaskDAO taskDAO =
            new TaskDAO();

    List<Reminder> reminderList =
            reminderDAO.getAllRemindersByUser(userId);

    List<Task> pendingTaskList =
            taskDAO.getPendingTaskList(userId);
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Reminder List</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->

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

            <div class="d-flex justify-content-between align-items-center mb-4">

                <h2 class="fw-bold">

                    <i class="fa-solid fa-bell text-primary"></i>

                    Reminder List

                </h2>

                <a href="add-reminder.jsp"
                   class="btn btn-primary">

                    <i class="fa-solid fa-plus"></i>

                    Add Reminder

                </a>

            </div>

            <!-- Messages -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Pending Tasks -->

            <div class="card shadow border-0 rounded-4 mb-4">

                <div class="card-header bg-white">

                    <h5 class="fw-bold mb-0">

                        <i class="fa-solid fa-clock text-warning"></i>

                        Pending Tasks Available For Reminder

                    </h5>

                </div>

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead class="table-light">

                            <tr>

                                <th>ID</th>

                                <th>Title</th>

                                <th>Due Date</th>

                                <th class="text-center">
                                    Action
                                </th>

                            </tr>

                            </thead>

                            <tbody>

                            <%
                                if (pendingTaskList != null
                                        && !pendingTaskList.isEmpty()) {

                                    for (Task task : pendingTaskList) {
                            %>

                            <tr>

                                <td>
                                    <%= task.getId() %>
                                </td>

                                <td>
                                    <%= task.getTitle() %>
                                </td>

                                <td>
                                    <%= task.getDueDate() %>
                                </td>

                                <td class="text-center">

                                    <a href="add-reminder.jsp?taskId=<%= task.getId() %>"
                                       class="btn btn-primary btn-sm">

                                        <i class="fa-solid fa-bell"></i>

                                        Set Reminder

                                    </a>

                                </td>

                            </tr>

                            <%
                                    }
                                } else {
                            %>

                            <tr>

                                <td colspan="4"
                                    class="text-center text-muted py-4">

                                    No Pending Tasks Found

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

            <!-- Reminder Table -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead class="table-dark">

                            <tr>

                                <th>ID</th>

                                <th>Task</th>

                                <th>Reminder Time</th>

                                <th>Message</th>

                                <th>Status</th>

                                <th class="text-center">
                                    Actions
                                </th>

                            </tr>

                            </thead>

                            <tbody>

                            <%
                                if (reminderList != null
                                        && !reminderList.isEmpty()) {

                                    for (Reminder reminder : reminderList) {
                            %>

                            <tr>

                                <!-- Reminder ID -->

                                <td>

                                    <%= reminder.getId() %>

                                </td>

                                <%
                                    Task task =
                                            taskDAO.getTaskById(reminder.getTaskId());
                                %>

                                <!-- Task -->

                                <td>

                                    <%= task != null ? task.getTitle() : "Task #" + reminder.getTaskId() %>

                                </td>

                                <!-- Reminder Time -->

                                <td>

                                    <%= reminder.getReminderTime() %>

                                </td>

                                <!-- Message -->

                                <td>

                                    <%= reminder.getMessage() %>

                                </td>

                                <!-- Status -->

                                <td>

                                    <%
                                        if ("Completed".equalsIgnoreCase(
                                                reminder.getStatus())) {
                                    %>

                                    <span class="badge bg-success">

                                        Completed

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

                                <!-- Actions -->

                                <td class="text-center">

                                    <!-- Edit -->

                                    <a href="edit-reminder.jsp?id=<%= reminder.getId() %>"
                                       class="btn btn-warning btn-sm">

                                        <i class="fa-solid fa-pen"></i>

                                    </a>

                                    <!-- Delete -->

                                    <a href="../reminder?action=delete&id=<%= reminder.getId() %>"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are You Sure To Delete This Reminder?')">

                                        <i class="fa-solid fa-trash"></i>

                                    </a>

                                </td>

                            </tr>

                            <%
                                    }
                                } else {
                            %>

                            <tr>

                                <td colspan="6"
                                    class="text-center text-muted py-4">

                                    No Reminders Found

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
