<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center min-vh-100">
<main class="container text-center">
    <h1 class="display-4 fw-bold text-danger">404</h1>
    <p class="lead mb-4">The page you requested was not found.</p>
    <a class="btn btn-primary" href="<%= request.getContextPath() %>/index.jsp">
        Back to Home
    </a>
</main>
</body>
</html>
