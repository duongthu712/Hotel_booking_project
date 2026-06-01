<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff Login</title>
</head>
<body>

<h2>Staff Login</h2>

<%
    String reset = request.getParameter("reset");
    Object error = request.getAttribute("error");

    if ("success".equals(reset)) {
%>
    <p style="color: green;">Password reset successfully. Please login again.</p>
<%
    }

    if (error != null && !error.toString().trim().isEmpty()) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/login" method="post">
    <label>Username:</label><br>
    <input type="text" name="username" required><br><br>

    <label>Password:</label><br>
    <input type="password" name="password" required><br><br>

    <button type="submit">Login</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/forgot-password">Forgot password?</a>

</body>
</html>