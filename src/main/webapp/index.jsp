<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession userSession =
            request.getSession(false);

    String contextPath = request.getContextPath();

    if (userSession != null
            && userSession.getAttribute("userId") != null) {

        response.sendRedirect(contextPath + "/dashboard");

        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Smart Task Management System</title>

    <!-- Bootstrap -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Font Awesome -->

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->

    <link rel="stylesheet"
          href="<%= contextPath %>/css/style.css">

</head>

<body class="bg-light">

<!-- Navbar -->

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">

    <div class="container">

        <!-- Logo -->

        <a class="navbar-brand fw-bold"
           href="<%= contextPath %>/index.jsp">

            <i class="fa-solid fa-list-check text-info"></i>

            Smart Task Manager

        </a>

        <!-- Mobile Toggle -->

        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarNav">

            <span class="navbar-toggler-icon"></span>

        </button>

        <!-- Menu -->

        <div class="collapse navbar-collapse"
             id="navbarNav">

            <ul class="navbar-nav ms-auto">

                <li class="nav-item">

                    <a class="nav-link"
                       href="<%= contextPath %>/auth/login.jsp">

                        <i class="fa-solid fa-right-to-bracket"></i>

                        Login

                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="<%= contextPath %>/auth/register.jsp">

                        <i class="fa-solid fa-user-plus"></i>

                        Register

                    </a>

                </li>

            </ul>

        </div>

    </div>

</nav>

<!-- Hero Section -->

<section class="py-5">

    <div class="container">

        <div class="row align-items-center min-vh-75">

            <!-- Left Content -->

            <div class="col-lg-6 mb-5 mb-lg-0">

                <h1 class="display-4 fw-bold mb-4">

                    Organize Your Tasks
                    <span class="text-primary">

                        Smartly

                    </span>

                </h1>

                <p class="lead text-muted mb-4">

                    Smart Task Management System helps you
                    manage tasks, reminders, categories,
                    progress tracking and productivity
                    efficiently using Advanced Java,
                    JSP, Servlet and MySQL.

                </p>

                <!-- Buttons -->

                <div class="d-flex flex-wrap gap-3">

                    <a href="<%= contextPath %>/auth/register.jsp"
                       class="btn btn-primary btn-lg px-4">

                        <i class="fa-solid fa-user-plus"></i>

                        Get Started

                    </a>

                    <a href="<%= contextPath %>/auth/login.jsp"
                       class="btn btn-outline-dark btn-lg px-4">

                        <i class="fa-solid fa-right-to-bracket"></i>

                        Login

                    </a>

                </div>

            </div>

            <!-- Right Image -->

            <div class="col-lg-6 text-center">

                <img src="<%= contextPath %>/images/logo.png"
                     alt="Smart Task Manager"
                     class="img-fluid"
                     style="max-height: 450px;">

            </div>

        </div>

    </div>

</section>

<!-- Features Section -->

<section class="py-5 bg-white">

    <div class="container">

        <!-- Section Title -->

        <div class="text-center mb-5">

            <h2 class="fw-bold">

                System Features

            </h2>

            <p class="text-muted">

                Powerful Features For Better Productivity

            </p>

        </div>

        <!-- Features -->

        <div class="row g-4">

            <!-- Feature 1 -->

            <div class="col-md-4">

                <div class="card shadow border-0 h-100 text-center p-4">

                    <div class="mb-3">

                        <i class="fa-solid fa-list-check fa-3x text-primary"></i>

                    </div>

                    <h4 class="fw-bold">

                        Task Management

                    </h4>

                    <p class="text-muted">

                        Create, update, delete and organize
                        tasks efficiently.

                    </p>

                </div>

            </div>

            <!-- Feature 2 -->

            <div class="col-md-4">

                <div class="card shadow border-0 h-100 text-center p-4">

                    <div class="mb-3">

                        <i class="fa-solid fa-bell fa-3x text-warning"></i>

                    </div>

                    <h4 class="fw-bold">

                        Smart Reminders

                    </h4>

                    <p class="text-muted">

                        Set reminders and never miss
                        important deadlines.

                    </p>

                </div>

            </div>

            <!-- Feature 3 -->

            <div class="col-md-4">

                <div class="card shadow border-0 h-100 text-center p-4">

                    <div class="mb-3">

                        <i class="fa-solid fa-chart-line fa-3x text-success"></i>

                    </div>

                    <h4 class="fw-bold">

                        Analytics Dashboard

                    </h4>

                    <p class="text-muted">

                        Track task progress and productivity
                        with charts and reports.

                    </p>

                </div>

            </div>

        </div>

    </div>

</section>

<!-- About Section -->

<section class="py-5">

    <div class="container">

        <div class="row align-items-center">

            <!-- Image -->

            <div class="col-lg-6 mb-4 mb-lg-0">

                <img src="<%= contextPath %>/images/background.jpg"
                     alt="Productivity"
                     class="img-fluid rounded-4 shadow">

            </div>

            <!-- Content -->

            <div class="col-lg-6">

                <h2 class="fw-bold mb-4">

                    Increase Your Productivity

                </h2>

                <p class="text-muted mb-4">

                    Our Smart Task Management System helps
                    students, professionals and teams
                    improve productivity with task planning,
                    reminders, progress tracking and reports.

                </p>

                <ul class="list-unstyled">

                    <li class="mb-3">

                        <i class="fa-solid fa-check text-success"></i>

                        Manage Daily Tasks Easily

                    </li>

                    <li class="mb-3">

                        <i class="fa-solid fa-check text-success"></i>

                        Track Progress In Real Time

                    </li>

                    <li class="mb-3">

                        <i class="fa-solid fa-check text-success"></i>

                        User Friendly Interface

                    </li>

                    <li class="mb-3">

                        <i class="fa-solid fa-check text-success"></i>

                        Secure Login And Authentication

                    </li>

                </ul>

            </div>

        </div>

    </div>

</section>

<!-- Footer -->

<footer class="bg-dark text-white py-4">

    <div class="container text-center">

        <h5 class="fw-bold">

            Smart Task Management System

        </h5>

        <p class="mb-2">

            Developed Using JSP, Servlet, JDBC And MySQL

        </p>

        <small>

            © 2026 All Rights Reserved | Developed By Mayur

        </small>

    </div>

</footer>

<!-- Bootstrap -->

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>
