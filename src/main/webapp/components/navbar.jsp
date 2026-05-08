<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String contextPath =
            request.getContextPath();

    HttpSession userSession =
            request.getSession(false);

    String userName = "User";

    String profileImage = "default.png";

    if (userSession != null) {

        if (userSession.getAttribute("userName") != null) {

            userName =
                    userSession.getAttribute("userName")
                            .toString();
        }

        if (userSession.getAttribute("profileImage") != null) {

            profileImage =
                    userSession.getAttribute("profileImage")
                            .toString();
        }
    }
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">

    <div class="container-fluid">

        <!-- Logo -->

        <a class="navbar-brand fw-bold"
           href="<%= contextPath %>/dashboard">

            <i class="fa-solid fa-list-check text-info"></i>

            Smart Task Manager

        </a>

        <!-- Mobile Toggle -->

        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarContent">

            <span class="navbar-toggler-icon"></span>

        </button>

        <!-- Navbar Content -->

        <div class="collapse navbar-collapse"
             id="navbarContent">

            <!-- Left Menu -->

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">

                    <a class="nav-link"
                       href="<%= contextPath %>/dashboard">

                        <i class="fa-solid fa-house"></i>

                        Dashboard

                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="<%= contextPath %>/task?action=list">

                        <i class="fa-solid fa-list"></i>

                        Tasks

                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="<%= contextPath %>/category/category-list.jsp">

                        <i class="fa-solid fa-layer-group"></i>

                        Categories

                    </a>

                </li>

                <li class="nav-item">

                    <a class="nav-link"
                       href="<%= contextPath %>/reminder/reminder-list.jsp">

                        <i class="fa-solid fa-bell"></i>

                        Reminders

                    </a>

                </li>

            </ul>

            <!-- Search Form -->

            <form class="d-flex me-3"
                  action="<%= contextPath %>/searchTask"
                  method="get">

                <input class="form-control me-2"
                       type="search"
                       name="keyword"
                       placeholder="Search Tasks">

                <button class="btn btn-outline-light"
                        type="submit">

                    <i class="fa-solid fa-magnifying-glass"></i>

                </button>

            </form>

            <!-- User Dropdown -->

            <div class="dropdown">

                <a class="d-flex align-items-center text-white text-decoration-none dropdown-toggle"
                   href="#"
                   role="button"
                   data-bs-toggle="dropdown">

                    <!-- Profile Image -->

                    <img src="<%= contextPath %>/uploads/profile-images/<%= profileImage %>"
                         alt="Profile"
                         width="40"
                         height="40"
                         class="rounded-circle border border-2 border-light me-2">

                    <!-- User Name -->

                    <span class="fw-semibold">

                        <%= userName %>

                    </span>

                </a>

                <!-- Dropdown Menu -->

                <ul class="dropdown-menu dropdown-menu-end shadow">

                    <li>

                        <a class="dropdown-item"
                           href="<%= contextPath %>/profile/profile.jsp">

                            <i class="fa-solid fa-user"></i>

                            My Profile

                        </a>

                    </li>

                    <li>

                        <a class="dropdown-item"
                           href="<%= contextPath %>/profile/change-password.jsp">

                            <i class="fa-solid fa-key"></i>

                            Change Password

                        </a>

                    </li>

                    <li>

                        <hr class="dropdown-divider">

                    </li>

                    <li>

                        <a class="dropdown-item text-danger"
                           href="<%= contextPath %>/logout">

                            <i class="fa-solid fa-right-from-bracket"></i>

                            Logout

                        </a>

                    </li>

                </ul>

            </div>

        </div>

    </div>

</nav>
