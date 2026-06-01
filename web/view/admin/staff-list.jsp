<%@ page import="java.util.List" %>
<%@ page import="model.StaffAccount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<StaffAccount> staffList = (List<StaffAccount>) request.getAttribute("staffList");
    String searchText = (String) request.getAttribute("searchText");
    String selectedRole = (String) request.getAttribute("role");

    if (searchText == null) {
        searchText = "";
    }

    if (selectedRole == null) {
        selectedRole = "ALL";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff Management</title>
</head>
<body>

<h2>Staff Management</h2>

<a href="${pageContext.request.contextPath}/login">Back to Dashboard</a> |
<a href="${pageContext.request.contextPath}/profile">My Profile</a> |
<a href="${pageContext.request.contextPath}/logout">Logout</a>

<hr>

<form action="${pageContext.request.contextPath}/staff-management" method="get">
    <input type="text" name="searchText" placeholder="Search staff"
           value="<%= searchText %>">

    <select name="role">
        <option value="ALL" <%= "ALL".equals(selectedRole) ? "selected" : "" %>>All Roles</option>
        <option value="Lễ tân" <%= "Lễ tân".equals(selectedRole) ? "selected" : "" %>>Lễ tân</option>
        <option value="Quản lý" <%= "Quản lý".equals(selectedRole) ? "selected" : "" %>>Quản lý</option>
        <option value="Quản trị viên" <%= "Quản trị viên".equals(selectedRole) ? "selected" : "" %>>Quản trị viên</option>
    </select>

    <button type="submit">Search</button>
</form>

<br>

<a href="${pageContext.request.contextPath}/staff-management?action=add">
    Add New Staff
</a>

<br><br>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Role</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <%
        if (staffList != null) {
            for (StaffAccount s : staffList) {
    %>
        <tr>
            <td><%= s.getStaffId() %></td>
            <td><%= s.getUsername() %></td>
            <td><%= s.getFullName() %></td>
            <td><%= s.getEmail() %></td>
            <td><%= s.getPhone() %></td>
            <td><%= s.getRole() %></td>
            <td><%= s.isActive() ? "Active" : "Inactive" %></td>
            <td>
                <a href="${pageContext.request.contextPath}/staff-management?action=edit&id=<%= s.getStaffId() %>">
                    Edit
                </a>

                |

                <% if (s.isActive()) { %>
                    <a href="${pageContext.request.contextPath}/staff-management?action=disable&id=<%= s.getStaffId() %>"
                       onclick="return confirm('Disable this staff account?')">
                        Disable
                    </a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/staff-management?action=enable&id=<%= s.getStaffId() %>">
                        Enable
                    </a>
                <% } %>
            </td>
        </tr>
    <%
            }
        }
    %>
</table>

</body>
</html>