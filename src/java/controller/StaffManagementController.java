package controller;

import dal.PasswordUtil;
import dao.StaffAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.StaffAccount;

public class StaffManagementController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>StaffManagementController</h1>");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("staff") == null) {
            return false;
        }

        StaffAccount staff = (StaffAccount) session.getAttribute("staff");
        String role = staff.getRole();

        if (role == null) {
            return false;
        }

        return role.equalsIgnoreCase("Administrator")
                || role.equalsIgnoreCase("Admin")
                || role.equalsIgnoreCase("Quản trị viên");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        StaffAccountDAO dao = new StaffAccountDAO();

        switch (action) {
            case "add": {
                request.getRequestDispatcher("/view/admin/staff-form.jsp").forward(request, response);
                break;
            }

            case "edit": {
                int staffId = Integer.parseInt(request.getParameter("id"));
                StaffAccount staff = dao.getStaffByIdIncludeInactive(staffId);

                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/view/admin/staff-form.jsp").forward(request, response);
                break;
            }

            case "disable": {
                int staffId = Integer.parseInt(request.getParameter("id"));
                dao.updateStaffStatus(staffId, false);
                response.sendRedirect(request.getContextPath() + "/staff-management");
                break;
            }

            case "enable": {
                int staffId = Integer.parseInt(request.getParameter("id"));
                dao.updateStaffStatus(staffId, true);
                response.sendRedirect(request.getContextPath() + "/staff-management");
                break;
            }

            default: {
                String searchText = request.getParameter("searchText");
                String role = request.getParameter("role");

                List<StaffAccount> staffList;

                if ((searchText != null && !searchText.trim().isEmpty())
                        || (role != null && !role.equals("ALL") && !role.trim().isEmpty())) {
                    staffList = dao.searchStaff(searchText, role);
                } else {
                    staffList = dao.getStaffAccounts();
                }

                request.setAttribute("staffList", staffList);
                request.setAttribute("searchText", searchText);
                request.setAttribute("role", role);

                RequestDispatcher rd = request.getRequestDispatcher("/view/admin/staff-list.jsp");
                rd.forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String staffIdRaw = request.getParameter("staffId");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String activeRaw = request.getParameter("active");

        boolean active = "1".equals(activeRaw);

        StaffAccountDAO dao = new StaffAccountDAO();

        StaffAccount staff = new StaffAccount();
        staff.setUsername(username);
        staff.setFullName(fullName);
        staff.setEmail(email);
        staff.setPhone(phone);
        staff.setRole(role);
        staff.setActive(active);

        if (staffIdRaw == null || staffIdRaw.trim().isEmpty()) {
            if (password == null || password.trim().isEmpty()) {
                request.setAttribute("error", "Password is required when creating staff.");
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("/view/admin/staff-form.jsp").forward(request, response);
                return;
            }

            staff.setPasswordHash(PasswordUtil.hashPassword(password));
            dao.createStaff(staff);

        } else {
            int staffId = Integer.parseInt(staffIdRaw);
            staff.setStaffId(staffId);

            dao.updateStaff(staff);

            if (password != null && !password.trim().isEmpty()) {
                String newPasswordHash = PasswordUtil.hashPassword(password);
                dao.updatePasswordByStaffId(staffId, newPasswordHash);
            }
        }

        response.sendRedirect(request.getContextPath() + "/staff-management");
    }

    @Override
    public String getServletInfo() {
        return "Staff Management Controller";
    }
}