<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Collection" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>

    <title>Error</title>
</head>

<body>
<div class="container pt-3">
    <div class="card text-white bg-dark text-center fw-light border-dark">
        <div class="card-header">
            <%
                Object statusCode = session.getAttribute("statusCode");
                Object errorMessage = session.getAttribute("errorMessage");
            %>
            Status Code: <%=statusCode%>
        </div>
        <div class="card-body border-dark">
            <h5 class="card-title fw-light">
                <%=errorMessage%>
            </h5>
            <br>
            <a href="http://localhost:8080/todo" class="btn btn-outline-warning btn-secondary fw-light btn-sm">Go to
                main page</a>
        </div>
        <div class="card-footer text-muted fw-light">
            Sorry
        </div>
    </div>
</div>
<!-- JavaScript Bundle with Popper -->

</body>

</html>
