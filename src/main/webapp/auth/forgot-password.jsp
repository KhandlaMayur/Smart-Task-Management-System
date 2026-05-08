<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession previewSession =
            request.getSession(false);

    String warning =
            request.getParameter("warning");

    String resetLinkPreview = null;

    if (previewSession != null
            && previewSession.getAttribute("resetLinkPreview") != null) {

        resetLinkPreview =
                previewSession.getAttribute("resetLinkPreview")
                        .toString();

        previewSession.removeAttribute("resetLinkPreview");
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Forgot Password</title>

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

                    <!-- Title -->

                    <div class="text-center mb-4">

                        <i class="fa-solid fa-lock fa-3x text-primary mb-3"></i>

                        <h2 class="fw-bold">
                            Forgot Password
                        </h2>

                        <p class="text-muted">
                            Enter your email to reset password
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

                    <%
                        if (warning != null) {
                    %>

                    <div class="alert alert-warning alert-dismissible fade show">

                        <%= warning %>

                        <button type="button"
                                class="btn-close"
                                data-bs-dismiss="alert">
                        </button>

                    </div>

                    <%
                        }
                    %>

                    <!-- Forgot Password Form -->

                    <form action="../forgotPassword"
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
                                       placeholder="Enter Registered Email"
                                       required>

                            </div>
                        </div>

                        <!-- Submit Button -->

                        <div class="d-grid mt-4">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-paper-plane"></i>

                                Send Reset Link

                            </button>
                        </div>

                    </form>

                    <%
                        if (resetLinkPreview != null
                                && !resetLinkPreview.trim().isEmpty()) {
                    %>

                    <div class="alert alert-info mt-4 mb-0">

                        <div class="fw-semibold mb-2">
                            Reset Link Preview
                        </div>

                        <a href="<%= resetLinkPreview %>"
                           class="small">

                            <%= resetLinkPreview %>

                        </a>

                        <div class="small text-muted mt-2">
                            Configure <code>src/main/resources/email.properties</code> to send this link to Gmail automatically.
                        </div>

                    </div>

                    <%
                        }
                    %>

                    <!-- Back To Login -->

                    <div class="text-center mt-4">

                        <a href="login.jsp"
                           class="text-decoration-none">

                            <i class="fa-solid fa-arrow-left"></i>

                            Back To Login

                        </a>

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
