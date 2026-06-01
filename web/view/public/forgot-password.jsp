<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
</head>
<body>

<h2>Forgot Password</h2>

<%
    Object error = request.getAttribute("error");

    if (error != null && !error.toString().trim().isEmpty()) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/forgot-password" method="post">
    <label>Enter your staff email:</label><br>
    <input type="email" name="email" required><br><br>

    <button type="submit">Send Code</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/login">Back to login</a>

</body>
</html>