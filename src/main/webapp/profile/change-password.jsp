<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession userSession =
            request.getSession(false);

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

    <title>Change Password</title>

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

                    <i class="fa-solid fa-key text-primary"></i>

                    Change Password

                </h2>

            </div>

            <!-- Messages -->

            <jsp:include page="../components/message.jsp"/>

            <!-- Change Password Card -->

            <div class="card shadow border-0 rounded-4">

                <div class="card-body p-5">

                    <form action="../profile"
                          method="post"
                          id="changePasswordForm">

                        <!-- Action -->

                        <input type="hidden"
                               name="action"
                               value="changePassword">

                        <!-- Current Password -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Current Password

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-lock"></i>

                                </span>

                                <input type="password"
                                       class="form-control"
                                       name="currentPassword"
                                       id="currentPassword"
                                       placeholder="Enter Current Password"
                                       required>

                            </div>

                        </div>

                        <!-- New Password -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                New Password

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-key"></i>

                                </span>

                                <input type="password"
                                       class="form-control"
                                       name="newPassword"
                                       id="newPassword"
                                       placeholder="Enter New Password"
                                       required>

                            </div>

                            <small class="text-muted">

                                Password must contain uppercase,
                                lowercase, number and special character.

                            </small>

                        </div>

                        <!-- Confirm Password -->

                        <div class="mb-4">

                            <label class="form-label fw-semibold">

                                Confirm Password

                            </label>

                            <div class="input-group">

                                <span class="input-group-text">

                                    <i class="fa-solid fa-check"></i>

                                </span>

                                <input type="password"
                                       class="form-control"
                                       name="confirmPassword"
                                       id="confirmPassword"
                                       placeholder="Confirm New Password"
                                       required>

                            </div>

                        </div>

                        <!-- Submit -->

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-floppy-disk"></i>

                                Update Password

                            </button>

                        </div>

                        <div class="text-end mt-3">

                            <a href="../auth/forgot-password.jsp"
                               class="text-decoration-none">

                                Forgot Current Password?

                            </a>

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

<!-- Validation JS -->

<script src="../js/validation.js">
</script>

</body>
</html>
