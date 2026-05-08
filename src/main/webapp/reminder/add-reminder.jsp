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

    List<Task> taskList =
            taskDAO.getPendingTaskList(userId);

    String selectedTaskId =
            request.getParameter("taskId");
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Reminder</title>

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

            <!-- Header -->

            <div class="d-flex justify-content-between align-items-center mb-4">

                <h2 class="fw-bold">

                    <i class="fa-solid fa-bell text-primary"></i>

                    Set Reminder

                </h2>

                <a href="reminder-list.jsp"
                   class="btn btn-secondary">

                    <i class="fa-solid fa-arrow-left"></i>

                    Back

                </a>

            </div>

            <!-- Messages -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Reminder Form -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <form action="../reminder"
                          method="post"
                          id="reminderForm">

                        <!-- Action -->

                        <input type="hidden"
                               name="action"
                               value="add">

                        <!-- Select Pending Task -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Pending Task

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-list-check"></i>

                                </span>

                                <select name="taskId"
                                        class="form-select"
                                        required>

                                    <option value="">
                                        Select Pending Task
                                    </option>

                                    <%
                                        if (taskList != null
                                                && !taskList.isEmpty()) {

                                            for (Task task : taskList) {
                                    %>

                                    <option value="<%= task.getId() %>"
                                        <%= String.valueOf(task.getId()).equals(selectedTaskId) ? "selected" : "" %>>

                                        <%= task.getTitle() %> | Due: <%= task.getDueDate() %>

                                    </option>

                                    <%
                                            }
                                        }
                                    %>

                                </select>

                            </div>

                        </div>

                        <!-- Reminder Date Time -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Reminder Date & Time

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-calendar-days"></i>

                                </span>

                                <input type="datetime-local"
                                       name="reminderTime"
                                       class="form-control"
                                       required>

                            </div>

                        </div>

                        <!-- Reminder Message -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Reminder Message

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-message"></i>

                                </span>

                                <textarea name="message"
                                          class="form-control"
                                          rows="4"
                                          placeholder="Enter Reminder Message"
                                          required></textarea>

                            </div>

                        </div>

                        <!-- Submit Button -->

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-floppy-disk"></i>

                                Save Reminder

                            </button>

                        </div>

                    </form>

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

<!-- Validation -->

<script src="../js/validation.js">
</script>

</body>
</html>
