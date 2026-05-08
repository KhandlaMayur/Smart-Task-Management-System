<%@ page import="dao.ReminderDAO" %>
<%@ page import="model.Reminder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    HttpSession userSession = request.getSession(false);
    if (userSession == null
            || userSession.getAttribute("userId") == null) {
        response.sendRedirect(contextPath + "/auth/login.jsp");
        return;
    }
    int userId = Integer.parseInt(userSession.getAttribute("userId").toString());
    String idParam = request.getParameter("id");
    if (idParam == null || idParam.trim().isEmpty()) {
        response.sendRedirect(contextPath + "/reminder/reminder-list.jsp?error=Invalid Reminder ID");
        return;
    }
    ReminderDAO reminderDAO = new ReminderDAO();
    Reminder reminder = reminderDAO.getReminderByIdForUser(Integer.parseInt(idParam), userId);
    if (reminder == null) {
        response.sendRedirect(contextPath + "/reminder/reminder-list.jsp?error=Reminder Not Found");
        return;
    }
    String reminderTimeValue = reminder.getReminderTime().toLocalDateTime().toString();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Reminder</title>
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
            <h2 class="fw-bold mb-4">Edit Reminder</h2>
            <jsp:include page="/components/message.jsp"/>
            <div class="card shadow border-0 rounded-4">
                <div class="card-body p-4">
                    <form action="<%= contextPath %>/reminder" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= reminder.getId() %>">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Reminder Time</label>
                            <input type="datetime-local" name="reminderTime" class="form-control" value="<%= reminderTimeValue %>" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Message</label>
                            <textarea name="message" class="form-control" rows="3" required><%= reminder.getMessage() %></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Status</label>
                            <select name="status" class="form-select" required>
                                <option value="Pending" <%= "Pending".equalsIgnoreCase(reminder.getStatus()) ? "selected" : "" %>>Pending</option>
                                <option value="Completed" <%= "Completed".equalsIgnoreCase(reminder.getStatus()) ? "selected" : "" %>>Completed</option>
                            </select>
                        </div>
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Update</button>
                            <a href="<%= contextPath %>/reminder/reminder-list.jsp" class="btn btn-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
            <jsp:include page="/components/footer.jsp"/>
        </div>
    </div>
</div>
</body>
</html>
