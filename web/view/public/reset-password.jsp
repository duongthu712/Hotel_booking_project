<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
</head>
<body>

<h2>Reset Password</h2>

<%
    Object error = request.getAttribute("error");

    if (error != null && !error.toString().trim().isEmpty()) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/reset-password" method="post">
    <label>New Password:</label><br>
    <input type="password" name="newPassword" required><br><br>

    <label>Confirm Password:</label><br>
    <input type="password" name="confirmPassword" required><br><br>

    <button type="submit">Reset Password</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/login">Back to login</a>

</body>
</html>