<%@ page import="model.StaffAccount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    StaffAccount staff = (StaffAccount) request.getAttribute("staff");
    boolean editMode = staff != null && staff.getStaffId() > 0;

    String title = editMode ? "Edit Staff" : "Add Staff";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= title %></title>
</head>
<body>

<h2><%= title %></h2>

<%
    Object error = request.getAttribute("error");

    if (error != null) {
%>
    <p style="color: red;"><%= error %></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/staff-management" method="post">

    <% if (editMode) { %>
        <input type="hidden" name="staffId" value="<%= staff.getStaffId() %>">
    <% } %>

    <label>Username:</label><br>
    <input type="text" name="username"
           value="<%= editMode ? staff.getUsername() : "" %>"
           required><br><br>

    <label>Password:</label><br>
    <input type="password" name="password">
    <br>
    <% if (editMode) { %>
        <small>Leave blank if you do not want to change password.</small>
    <% } else { %>
        <small>Required for new staff.</small>
    <% } %>
    <br><br>

    <label>Full Name:</label><br>
    <input type="text" name="fullName"
           value="<%= editMode ? staff.getFullName() : "" %>"
           required><br><br>

    <label>Email:</label><br>
    <input type="email" name="email"
           value="<%= editMode ? staff.getEmail() : "" %>"
           required><br><br>

    <label>Phone:</label><br>
    <input type="text" name="phone"
           value="<%= editMode ? staff.getPhone() : "" %>"><br><br>

    <label>Role:</label><br>
    <select name="role" required>
        <option value="Lễ tân"
            <%= editMode && "Lễ tân".equals(staff.getRole()) ? "selected" : "" %>>
            Lễ tân
        </option>

        <option value="Quản lý"
            <%= editMode && "Quản lý".equals(staff.getRole()) ? "selected" : "" %>>
            Quản lý
        </option>

        <option value="Quản trị viên"
            <%= editMode && "Quản trị viên".equals(staff.getRole()) ? "selected" : "" %>>
            Quản trị viên
        </option>
    </select><br><br>

    <label>Status:</label><br>
    <select name="active">
        <option value="1" <%= !editMode || staff.isActive() ? "selected" : "" %>>
            Active
        </option>

        <option value="0" <%= editMode && !staff.isActive() ? "selected" : "" %>>
            Inactive
        </option>
    </select><br><br>

    <button type="submit">Save</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/staff-management">Back to Staff List</a>

</body>
</html>