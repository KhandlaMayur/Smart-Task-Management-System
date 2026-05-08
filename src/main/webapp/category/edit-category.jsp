<%@ page import="dao.CategoryDAO" %>
<%@ page import="model.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getContextPath();
    String idParam = request.getParameter("id");
    if (idParam == null || idParam.trim().isEmpty()) {
        response.sendRedirect(contextPath + "/category/category-list.jsp?error=Invalid Category ID");
        return;
    }
    CategoryDAO categoryDAO = new CategoryDAO();
    Category category = categoryDAO.getCategoryById(Integer.parseInt(idParam));
    if (category == null) {
        response.sendRedirect(contextPath + "/category/category-list.jsp?error=Category Not Found");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Category</title>
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
            <h2 class="fw-bold mb-4">Edit Category</h2>
            <jsp:include page="/components/message.jsp"/>
            <div class="card shadow border-0 rounded-4">
                <div class="card-body p-4">
                    <form action="<%= contextPath %>/category" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= category.getId() %>">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Category Name</label>
                            <input type="text" name="categoryName" class="form-control" value="<%= category.getCategoryName() %>" required>
                        </div>
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Update</button>
                            <a href="<%= contextPath %>/category/category-list.jsp" class="btn btn-secondary">Cancel</a>
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
