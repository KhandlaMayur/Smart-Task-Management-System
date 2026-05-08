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

    <title>Edit Profile</title>

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

                    <i class="fa-solid fa-user-pen text-primary"></i>

                    Edit Profile

                </h2>

            </div>

            <!-- Message -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Edit Profile Card -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <form action="../profile"
                          method="post"
                          enctype="multipart/form-data"
                          id="profileForm">

                        <!-- Action -->

                        <input type="hidden"
                               name="action"
                               value="updateProfile">

                        <!-- Profile Image -->

                        <div class="text-center mb-4">

                            <img src="../uploads/profile-images/<%= user.getProfileImage() %>"
                                 alt="Profile Image"
                                 class="profile-image mb-3"
                                 id="previewImage">

                            <div>

                                <input type="file"
                                       name="profileImage"
                                       id="fileUpload"
                                       class="form-control"
                                       accept=".jpg,.jpeg,.png">

                            </div>

                        </div>

                        <!-- Name -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Full Name

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-user"></i>

                                </span>

                                <input type="text"
                                       class="form-control"
                                       name="name"
                                       id="name"
                                       value="<%= user.getName() %>"
                                       placeholder="Enter Full Name"
                                       required>

                            </div>

                        </div>

                        <!-- Email -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Email Address

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-envelope"></i>

                                </span>

                                <input type="email"
                                       class="form-control"
                                       name="email"
                                       id="email"
                                       value="<%= user.getEmail() %>"
                                       placeholder="Enter Email"
                                       required>

                            </div>

                        </div>

                        <!-- Submit -->

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-floppy-disk"></i>

                                Update Profile

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

<!-- Bootstrap JS -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

<!-- Main JS -->

<script src="../js/main.js">
</script>

<!-- Validation JS -->

<script src="../js/validation.js">
</script>

</body>
</html>