<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Verify Code</title>
</head>
<body>

<h2>Verify Reset Code</h2>

<%
    Object message = request.getAttribute("message");
    Object error = request.getAttribute("error");

    if (message != null && !message.toString().trim().isEmpty()) {
%>
    <p style="color: green;"><%= message %></p>
<%
    }

    if (error != null && !error.toString().trim().isEmpty()) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/verify-code" method="post">
    <label>Reset Code:</label><br>
    <input type="text" name="code" required><br><br>

    <button type="submit">Verify Code</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/forgot-password">Send code again</a>

</body>
</html>