<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");

    String warning =
            request.getParameter("warning");

    String info =
            request.getParameter("info");
%>

<!-- ========================================= -->
<!-- SUCCESS MESSAGE -->
<!-- ========================================= -->

<%
    if (success != null
            && !success.trim().isEmpty()) {
%>

<div class="alert alert-success alert-dismissible fade show shadow-sm"
     role="alert">

    <i class="fa-solid fa-circle-check"></i>

    <strong>
        Success :
    </strong>

    <%= success %>

    <button type="button"
            class="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close">
    </button>

</div>

<%
    }
%>

<!-- ========================================= -->
<!-- ERROR MESSAGE -->
<!-- ========================================= -->

<%
    if (error != null
            && !error.trim().isEmpty()) {
%>

<div class="alert alert-danger alert-dismissible fade show shadow-sm"
     role="alert">

    <i class="fa-solid fa-triangle-exclamation"></i>

    <strong>
        Error :
    </strong>

    <%= error %>

    <button type="button"
            class="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close">
    </button>

</div>

<%
    }
%>

<!-- ========================================= -->
<!-- WARNING MESSAGE -->
<!-- ========================================= -->

<%
    if (warning != null
            && !warning.trim().isEmpty()) {
%>

<div class="alert alert-warning alert-dismissible fade show shadow-sm"
     role="alert">

    <i class="fa-solid fa-circle-exclamation"></i>

    <strong>
        Warning :
    </strong>

    <%= warning %>

    <button type="button"
            class="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close">
    </button>

</div>

<%
    }
%>

<!-- ========================================= -->
<!-- INFO MESSAGE -->
<!-- ========================================= -->

<%
    if (info != null
            && !info.trim().isEmpty()) {
%>

<div class="alert alert-info alert-dismissible fade show shadow-sm"
     role="alert">

    <i class="fa-solid fa-circle-info"></i>

    <strong>
        Info :
    </strong>

    <%= info %>

    <button type="button"
            class="btn-close"
            data-bs-dismiss="alert"
            aria-label="Close">
    </button>

</div>

<%
    }
%>