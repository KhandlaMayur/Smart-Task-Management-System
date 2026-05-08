<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page import="model.Category" %>
<%@ page import="dao.TaskDAO" %>
<%@ page import="dao.CategoryDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession userSession =
            request.getSession(false);

    if (userSession == null
            || userSession.getAttribute("userId") == null) {

        response.sendRedirect("../auth/login.jsp");

        return;
    }

    int taskId =
            Integer.parseInt(
                    request.getParameter("id")
            );

    TaskDAO taskDAO =
            new TaskDAO();

    Task task =
            taskDAO.getTaskById(taskId);

    CategoryDAO categoryDAO =
            new CategoryDAO();

    List<Category> categoryList =
            categoryDAO.getAllCategories();

    if (task == null) {

        response.sendRedirect(
                "../task?action=list&error=Task Not Found"
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

    <title>Edit Task</title>

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

                    <i class="fa-solid fa-pen-to-square text-primary"></i>

                    Edit Task

                </h2>

                <a href="../task?action=list"
                   class="btn btn-secondary">

                    <i class="fa-solid fa-arrow-left"></i>

                    Back

                </a>

            </div>

            <!-- Messages -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Edit Task Form -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <form action="../updateTask"
                          method="post"
                          enctype="multipart/form-data"
                          id="taskForm">

                        <!-- Task ID -->

                        <input type="hidden"
                               name="id"
                               value="<%= task.getId() %>">

                        <!-- Title -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Task Title

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-heading"></i>

                                </span>

                                <input type="text"
                                       name="title"
                                       id="title"
                                       class="form-control"
                                       value="<%= task.getTitle() %>"
                                       required>

                            </div>

                        </div>

                        <!-- Description -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Description

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-file-lines"></i>

                                </span>

                                <textarea name="description"
                                          id="description"
                                          rows="5"
                                          class="form-control"
                                          required><%= task.getDescription() %></textarea>

                            </div>

                        </div>

                        <!-- Priority -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Priority

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-flag"></i>

                                </span>

                                <select name="priority"
                                        class="form-select"
                                        required>

                                    <option value="Low"
                                        <%= "Low".equals(task.getPriority()) ? "selected" : "" %>>

                                        Low

                                    </option>

                                    <option value="Medium"
                                        <%= "Medium".equals(task.getPriority()) ? "selected" : "" %>>

                                        Medium

                                    </option>

                                    <option value="High"
                                        <%= "High".equals(task.getPriority()) ? "selected" : "" %>>

                                        High

                                    </option>

                                </select>

                            </div>

                        </div>

                        <!-- Status -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Status

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-circle-info"></i>

                                </span>

                                <select name="status"
                                        class="form-select"
                                        required>

                                    <option value="Pending"
                                        <%= "Pending".equals(task.getStatus()) ? "selected" : "" %>>

                                        Pending

                                    </option>

                                    <option value="In Progress"
                                        <%= "In Progress".equals(task.getStatus()) ? "selected" : "" %>>

                                        In Progress

                                    </option>

                                    <option value="Completed"
                                        <%= "Completed".equals(task.getStatus()) ? "selected" : "" %>>

                                        Completed

                                    </option>

                                </select>

                            </div>

                        </div>

                        <!-- Due Date -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Due Date

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-calendar-days"></i>

                                </span>

                                <input type="date"
                                       name="dueDate"
                                       id="dueDate"
                                       class="form-control"
                                       value="<%= task.getDueDate() %>"
                                       required>

                            </div>

                        </div>

                        <!-- Category -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Category

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-layer-group"></i>

                                </span>

                                <select name="categoryId"
                                        class="form-select"
                                        required>

                                    <%
                                        for (Category category : categoryList) {
                                    %>

                                    <option value="<%= category.getId() %>"
                                        <%= category.getId() == task.getCategoryId() ? "selected" : "" %>>

                                        <%= category.getCategoryName() %>

                                    </option>

                                    <%
                                        }
                                    %>

                                </select>

                            </div>

                        </div>

                        <!-- Existing File -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Existing File

                            </label>

                            <div>

                                <%
                                    if (task.getTaskFile() != null
                                            && !task.getTaskFile().isEmpty()) {
                                %>

                                <a href="../uploads/task-files/<%= task.getTaskFile() %>"
                                   target="_blank"
                                   class="btn btn-outline-primary">

                                    <i class="fa-solid fa-file"></i>

                                    View File

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

                        <!-- Upload New File -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Upload New File

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-upload"></i>

                                </span>

                                <input type="file"
                                       name="taskFile"
                                       class="form-control"
                                       accept=".pdf,.doc,.docx,.txt,.png,.jpg,.jpeg">

                            </div>

                        </div>

                        <!-- Submit -->

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-floppy-disk"></i>

                                Update Task

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