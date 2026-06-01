<%@ page import="model.StaffAccount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    StaffAccount staff = (StaffAccount) request.getAttribute("staff");

    if (staff == null) {
        staff = (StaffAccount) session.getAttribute("staff");
    }

    if (staff == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>
</head>
<body>

<h2>My Profile</h2>

<p><strong>Username:</strong> <%= staff.getUsername() %></p>
<p><strong>Full name:</strong> <%= staff.getFullName() %></p>
<p><strong>Email:</strong> <%= staff.getEmail() %></p>
<p><strong>Phone:</strong> <%= staff.getPhone() %></p>
<p><strong>Role:</strong> <%= staff.getRole() %></p>

<hr>

<h3>Change Password</h3>

<%
    Object message = request.getAttribute("message");
    Object error = request.getAttribute("error");

    if (message != null) {
%>
    <p style="color: green;"><%= message %></p>
<%
    }

    if (error != null) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/profile" method="post">
    <label>Current Password:</label><br>
    <input type="password" name="currentPassword" required><br><br>

    <label>New Password:</label><br>
    <input type="password" name="newPassword" required><br><br>

    <label>Confirm New Password:</label><br>
    <input type="password" name="confirmPassword" required><br><br>

    <button type="submit">Change Password</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/login">Back to Dashboard</a><br>
<a href="${pageContext.request.contextPath}/logout">Logout</a>

</body>
</html>