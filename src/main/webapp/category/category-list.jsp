<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%@ page import="dao.CategoryDAO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession userSession = request.getSession(false);

    if (userSession == null
            || userSession.getAttribute("userId") == null) {

        response.sendRedirect("../auth/login.jsp");

        return;
    }

    CategoryDAO categoryDAO =
            new CategoryDAO();

    List<Category> categoryList =
            categoryDAO.getAllCategories();
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Category List</title>

    <!-- Bootstrap 5 -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- Custom CSS -->

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

                    <i class="fa-solid fa-layer-group text-primary"></i>

                    Category List

                </h2>

                <a href="add-category.jsp"
                   class="btn btn-primary">

                    <i class="fa-solid fa-plus"></i>

                    Add Category

                </a>

            </div>

            <!-- Success Message -->

            <%
                String success =
                        request.getParameter("success");

                if (success != null) {
            %>

            <div class="alert alert-success alert-dismissible fade show">

                <%= success %>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="alert">
                </button>

            </div>

            <%
                }
            %>

            <!-- Error Message -->

            <%
                String error =
                        request.getParameter("error");

                if (error != null) {
            %>

            <div class="alert alert-danger alert-dismissible fade show">

                <%= error %>

                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="alert">
                </button>

            </div>

            <%
                }
            %>

            <!-- Category Table -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body">

                    <div class="table-responsive">

                        <table class="table table-hover align-middle">

                            <thead class="table-dark">

                            <tr>

                                <th>ID</th>

                                <th>Category Name</th>

                                <th class="text-center">
                                    Actions
                                </th>

                            </tr>

                            </thead>

                            <tbody>

                            <%
                                if (categoryList != null
                                        && !categoryList.isEmpty()) {

                                    for (Category category : categoryList) {
                            %>

                            <tr>

                                <td>
                                    <%= category.getId() %>
                                </td>

                                <td>
                                    <%= category.getCategoryName() %>
                                </td>

                                <td class="text-center">

                                    <!-- Edit Button -->

                                    <a href="edit-category.jsp?id=<%= category.getId() %>"
                                       class="btn btn-warning btn-sm">

                                        <i class="fa-solid fa-pen"></i>

                                    </a>

                                    <!-- Delete Button -->

                                    <a href="../category?action=delete&id=<%= category.getId() %>"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are You Sure To Delete This Category?')">

                                        <i class="fa-solid fa-trash"></i>

                                    </a>

                                </td>

                            </tr>

                            <%
                                    }
                                } else {
                            %>

                            <tr>

                                <td colspan="3"
                                    class="text-center text-muted py-4">

                                    No Categories Found

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

        </div>

    </div>

</div>

<!-- Bootstrap JS -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>