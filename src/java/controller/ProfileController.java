package controller;

import dal.PasswordUtil;
import dao.StaffAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.StaffAccount;

public class ProfileController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<h1>ProfileController</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        StaffAccount sessionStaff = (StaffAccount) session.getAttribute("staff");

        StaffAccountDAO dao = new StaffAccountDAO();
        StaffAccount staff = dao.getStaffById(sessionStaff.getStaffId());

        request.setAttribute("staff", staff);

        RequestDispatcher rd = request.getRequestDispatcher("/view/auth/profile.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        StaffAccount sessionStaff = (StaffAccount) session.getAttribute("staff");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        StaffAccountDAO dao = new StaffAccountDAO();
        StaffAccount staff = dao.getStaffById(sessionStaff.getStaffId());

        if (staff == null) {
            request.setAttribute("error", "Staff account not found.");
            request.getRequestDispatcher("/view/auth/profile.jsp").forward(request, response);
            return;
        }

        if (!PasswordUtil.checkPassword(currentPassword, staff.getPasswordHash())) {
            request.setAttribute("staff", staff);
            request.setAttribute("error", "Current password is incorrect.");
            request.getRequestDispatcher("/view/auth/profile.jsp").forward(request, response);
            return;
        }

        if (newPassword == null || newPassword.length() < 6) {
            request.setAttribute("staff", staff);
            request.setAttribute("error", "New password must be at least 6 characters.");
            request.getRequestDispatcher("/view/auth/profile.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("staff", staff);
            request.setAttribute("error", "Confirm password does not match.");
            request.getRequestDispatcher("/view/auth/profile.jsp").forward(request, response);
            return;
        }

        String newPasswordHash = PasswordUtil.hashPassword(newPassword);
        dao.updatePasswordByStaffId(staff.getStaffId(), newPasswordHash);

        StaffAccount updatedStaff = dao.getStaffById(staff.getStaffId());
        session.setAttribute("staff", updatedStaff);

        request.setAttribute("staff", updatedStaff);
        request.setAttribute("message", "Password changed successfully.");

        request.getRequestDispatcher("/view/auth/profile.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Profile Controller";
    }
}