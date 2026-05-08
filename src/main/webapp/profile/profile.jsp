<%@ page import="model.User" %>
<%@ page import="dao.UserDAO" %>

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

    UserDAO userDAO =
            new UserDAO();

    User user =
            userDAO.getUserById(userId);
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>My Profile</title>

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

                    <i class="fa-solid fa-user text-primary"></i>

                    My Profile

                </h2>

                <a href="edit-profile.jsp"
                   class="btn btn-primary">

                    <i class="fa-solid fa-user-pen"></i>

                    Edit Profile

                </a>

            </div>

            <!-- Messages -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Profile Card -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <div class="row align-items-center">

                        <!-- Profile Image -->

                        <div class="col-md-4 text-center">

                            <img src="../uploads/profile-images/<%= user.getProfileImage() %>"
                                 alt="Profile Image"
                                 class="profile-image shadow">

                        </div>

                        <!-- User Details -->

                        <div class="col-md-8">

                            <div class="mb-4">

                                <h3 class="fw-bold">

                                    <%= user.getName() %>

                                </h3>

                                <p class="text-muted">

                                    Smart Task Management User

                                </p>

                            </div>

                            <!-- Information Table -->

                            <table class="table table-borderless">

                                <tr>

                                    <th width="200">

                                        <i class="fa-solid fa-id-badge text-primary"></i>

                                        User ID

                                    </th>

                                    <td>

                                        <%= user.getId() %>

                                    </td>

                                </tr>

                                <tr>

                                    <th>

                                        <i class="fa-solid fa-user text-primary"></i>

                                        Full Name

                                    </th>

                                    <td>

                                        <%= user.getName() %>

                                    </td>

                                </tr>

                                <tr>

                                    <th>

                                        <i class="fa-solid fa-envelope text-primary"></i>

                                        Email Address

                                    </th>

                                    <td>

                                        <%= user.getEmail() %>

                                    </td>

                                </tr>

                                <tr>

                                    <th>

                                        <i class="fa-solid fa-calendar-days text-primary"></i>

                                        Account Created

                                    </th>

                                    <td>

                                        <%= user.getCreatedAt() %>

                                    </td>

                                </tr>

                            </table>

                            <!-- Action Buttons -->

                            <div class="mt-4">

                                <a href="edit-profile.jsp"
                                   class="btn btn-primary me-2">

                                    <i class="fa-solid fa-user-pen"></i>

                                    Edit Profile

                                </a>

                                <a href="change-password.jsp"
                                   class="btn btn-warning text-white">

                                    <i class="fa-solid fa-key"></i>

                                    Change Password

                                </a>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

            <!-- Footer -->

            <jsp:include page="../components/footer.jsp"/>

        </div>

    </div>

</div>

<!-- Bootstrap JS -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>