<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession userSession = request.getSession(false);

    if (userSession == null
            || userSession.getAttribute("userId") == null) {

        response.sendRedirect("../auth/login.jsp");

        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Add Category</title>

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

                    Add Category

                </h2>

                <a href="category-list.jsp"
                   class="btn btn-secondary">

                    <i class="fa-solid fa-arrow-left"></i>

                    Back

                </a>

            </div>

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

            <!-- Add Category Card -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <form action="../category"
                          method="post">

                        <!-- Hidden Action -->

                        <input type="hidden"
                               name="action"
                               value="add">

                        <!-- Category Name -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Category Name

                            </label>

                            <div class="input-group">

                                    <span class="input-group-text">

                                        <i class="fa-solid fa-folder"></i>

                                    </span>

                                <input type="text"
                                       name="categoryName"
                                       class="form-control"
                                       placeholder="Enter Category Name"
                                       required>

                            </div>

                        </div>

                        <!-- Submit Button -->

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-plus"></i>

                                Add Category

                            </button>

                        </div>

                    </form>

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