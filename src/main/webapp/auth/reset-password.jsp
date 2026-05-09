<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String contextPath =
            request.getContextPath();

    String token =
            (String) request.getAttribute("token");

    if (token == null) {

        token = request.getParameter("token");
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Reset Password</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          href="<%= contextPath %>/css/style.css">

</head>

<body class="bg-light">

<div class="container">

    <div class="row justify-content-center align-items-center vh-100">

        <div class="col-md-5">

            <div class="card shadow-lg border-0 rounded-4">

                <div class="card-body p-5">

                    <div class="text-center mb-4">

                        <i class="fa-solid fa-key fa-3x text-primary mb-3"></i>

                        <h2 class="fw-bold">
                            Reset Password
                        </h2>

                        <p class="text-muted">
                            Set a new password for your account
                        </p>

                    </div>

                    <jsp:include page="../components/message.jsp"/>

                    <form action="<%= contextPath %>/resetPassword"
                          method="post">

                        <input type="hidden"
                               name="token"
                               value="<%= token %>">

                        <div class="mb-4">

                            <label class="form-label fw-semibold">
                                New Password
                            </label>

                            <div class="input-group">

                                <span class="input-group-text">
                                    <i class="fa-solid fa-lock"></i>
                                </span>

                                <input type="password"
                                       name="newPassword"
                                       class="form-control"
                                       placeholder="Enter New Password"
                                       required>

                            </div>

                        </div>

                        <div class="mb-4">

                            <label class="form-label fw-semibold">
                                Confirm Password
                            </label>

                            <div class="input-group">

                                <span class="input-group-text">
                                    <i class="fa-solid fa-check"></i>
                                </span>

                                <input type="password"
                                       name="confirmPassword"
                                       class="form-control"
                                       placeholder="Confirm New Password"
                                       required>

                            </div>

                            <small class="text-muted">
                                Password must contain uppercase, lowercase, number and special character.
                            </small>

                        </div>

                        <div class="d-grid">

                            <button type="submit"
                                    class="btn btn-primary btn-lg rounded-3">

                                <i class="fa-solid fa-floppy-disk"></i>

                                Reset Password

                            </button>

                        </div>

                    </form>

                    <div class="text-center mt-4">

                        <a href="<%= contextPath %>/auth/login.jsp"
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>
