<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String contextPath =
            request.getContextPath();
%>

<div class="bg-dark text-white vh-100 shadow-sm sidebar-wrapper">

    <!-- Sidebar Header -->

    <div class="p-4 border-bottom border-secondary text-center">

        <h4 class="fw-bold">

            <i class="fa-solid fa-bars-progress text-info"></i>

            Menu

        </h4>

    </div>

    <!-- Sidebar Menu -->

    <ul class="nav flex-column p-3">

        <!-- Dashboard -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/dashboard"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-house me-2"></i>

                Dashboard

            </a>

        </li>

        <!-- Task Management -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/task?action=list"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-list-check me-2"></i>

                Manage Tasks

            </a>

        </li>

        <!-- Add Task -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/task/add-task.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-square-plus me-2"></i>

                Add Task

            </a>

        </li>

        <!-- Search Task -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/searchTask"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-magnifying-glass me-2"></i>

                Search Task

            </a>

        </li>

        <!-- Categories -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/category/category-list.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-layer-group me-2"></i>

                Categories

            </a>

        </li>

        <!-- Add Category -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/category/add-category.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-folder-plus me-2"></i>

                Add Category

            </a>

        </li>

        <!-- Reminders -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/reminder/reminder-list.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-bell me-2"></i>

                Reminders

            </a>

        </li>

        <!-- Profile -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/profile/profile.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-user me-2"></i>

                My Profile

            </a>

        </li>

        <!-- Change Password -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/profile/change-password.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-key me-2"></i>

                Change Password

            </a>

        </li>

        <!-- Reports -->

        <li class="nav-item mb-2">

            <a href="<%= contextPath %>/reports/task-report.jsp"
               class="nav-link text-white sidebar-link">

                <i class="fa-solid fa-chart-column me-2"></i>

                Reports

            </a>

        </li>

        <!-- Logout -->

        <li class="nav-item mt-4">

            <a href="<%= contextPath %>/logout"
               class="nav-link text-danger sidebar-link">

                <i class="fa-solid fa-right-from-bracket me-2"></i>

                Logout

            </a>

        </li>

    </ul>

</div>

<!-- Sidebar Style -->

<style>

    .sidebar-wrapper {

        min-height: 100vh;
    }

    .sidebar-link {

        border-radius: 10px;

        padding: 12px 15px;

        transition: all 0.3s ease;
    }

    .sidebar-link:hover {

        background-color: #0d6efd;

        color: white !important;

        transform: translateX(5px);
    }

</style>
