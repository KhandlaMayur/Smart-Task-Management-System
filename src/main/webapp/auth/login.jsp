<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Login - Smart Task Management</title>

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

<div class="container">

    <div class="row justify-content-center align-items-center vh-100">

        <div class="col-md-5">

            <div class="card shadow-lg border-0 rounded-4">

                <div class="card-body p-5">

                    <!-- Logo -->

                    <div class="text-center mb-4">

                        <img src="../images/logo.png"
                             alt="Logo"
                             width="80"
                             class="mb-3">

                        <h2 class="fw-bold">
                            Login
                        </h2>

                        <p class="text-muted">
                            Smart Task Management System
                        </p>

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

                    <!-- Login Form -->

                    <form action="../login"
                          method="post">

                        <!-- Email -->

                        <div class="mb-3">

                            <label class="form-label fw-semibold">
                                Email Address
                            </label>

                            <div class="input-group">

                                    <span class="input-group-text">
                                        <i class="fa-solid fa-envelope"></i>
                                    </span>

                                <input type="email"
                                       name="email"
                                       class="form-control"
                                       placeholder="Enter Email"
                                       required>

                            </div>
                        </div>

                        <!-- Password -->

                        <div class="mb-3">

                            <label class="form-label fw-semibold">
                                Password
                            </label>

                            <div class="input-group">

                                    <span class="input-group-text">
                                        <i class="fa-solid fa-lock"></i>
                                    </span>

                                <input type="password"
                                       name="password"
                                       class="form-control"
                                       placeholder="Enter Password"
                                       required>

                            </div>
                        </div>

                        <!-- Forgot Password -->

                        <div class="text-end mb-3">

                            <a href="forgot-password.jsp"
                               class="text-decoration-none">

                                Forgot Password?

                            </a>

                        </div>

                        <!-- Login Button -->

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-right-to-bracket"></i>

                                Login

                            </button>

                        </div>

                    </form>

                    <!-- Register Link -->

                    <div class="text-center mt-4">

                        <p class="mb-0">

                            Don't have an account?

                            <a href="register.jsp"
                               class="text-decoration-none fw-semibold">

                                Register

                            </a>

                        </p>

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