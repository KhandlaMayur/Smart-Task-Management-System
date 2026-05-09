<%@ page import="java.util.List" %>
<%@ page import="dao.TaskDAO" %>
<%@ page import="model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    HttpSession userSession = request.getSession(false);
    if (userSession == null || userSession.getAttribute("userId") == null) {
        response.sendRedirect(contextPath + "/auth/login.jsp?error=Please Login First");
        return;
    }
    int userId = Integer.parseInt(userSession.getAttribute("userId").toString());
    TaskDAO taskDAO = new TaskDAO();
    List<Task> taskList = taskDAO.getAllTasks(userId);
    int completedTaskCount = taskDAO.getCompletedTasks(userId);
    int pendingTaskCount = taskDAO.getPendingTasks(userId);
    int inProgressTaskCount = taskDAO.getInProgressTasks(userId);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Task Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= contextPath %>/css/style.css">
</head>
<body class="bg-light">
<jsp:include page="/components/navbar.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-2 p-0"><jsp:include page="/components/sidebar.jsp"/></div>
        <div class="col-md-10 p-4">
            <div class="d-flex justify-content-between align-items-center flex-wrap gap-3 mb-4">
                <div>
                    <h2 class="fw-bold mb-1">Task Report</h2>
                    <p class="text-muted mb-0">View and download your task report receipt.</p>
                </div>
                <a href="<%= contextPath %>/downloadTaskReport"
                   class="btn btn-primary">
                    <i class="fa-solid fa-download"></i>
                    Download Report
                </a>
            </div>
            <div class="row g-3 mb-4">
                <div class="col-md-3 col-sm-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <div class="text-muted small mb-1">Total Tasks</div>
                            <div class="fs-3 fw-bold"><%= taskList.size() %></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <div class="text-muted small mb-1">Completed</div>
                            <div class="fs-3 fw-bold text-success"><%= completedTaskCount %></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <div class="text-muted small mb-1">Pending</div>
                            <div class="fs-3 fw-bold text-warning"><%= pendingTaskCount %></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-sm-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <div class="text-muted small mb-1">In Progress</div>
                            <div class="fs-3 fw-bold text-primary"><%= inProgressTaskCount %></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card shadow border-0 rounded-4">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped align-middle">
                            <thead>
                            <tr><th>Title</th><th>Priority</th><th>Status</th><th>Due Date</th></tr>
                            </thead>
                            <tbody>
                            <% if (taskList != null && !taskList.isEmpty()) { for (Task task : taskList) { %>
                            <tr>
                                <td><%= task.getTitle() %></td>
                                <td><%= task.getPriority() %></td>
                                <td><%= task.getStatus() %></td>
                                <td><%= task.getDueDate() %></td>
                            </tr>
                            <% }} else { %>
                            <tr><td colspan="4" class="text-center text-muted">No tasks found</td></tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <jsp:include page="/components/footer.jsp"/>
        </div>
    </div>
</div>
</body>
</html>
