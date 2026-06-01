package controller;

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

public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        StaffAccount staff = (StaffAccount) session.getAttribute("staff");

        if (staff == null) {
            RequestDispatcher rd = request.getRequestDispatcher("/view/public/login.jsp");
            rd.forward(request, response);
        } else {
            redirectByRole(request, response, staff);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("===== LOGIN DEBUG =====");
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        StaffAccountDAO dao = new StaffAccountDAO();
        StaffAccount staff = dao.loginWithHashCheck(username, password);

        if (staff != null) {
            HttpSession session = request.getSession();

            session.setAttribute("staff", staff);
            session.setAttribute("staffId", staff.getStaffId());
            session.setAttribute("staffRole", staff.getRole());

            response.sendRedirect(request.getContextPath() + "/login");

        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/view/public/login.jsp").forward(request, response);
        }
    }

    private void redirectByRole(HttpServletRequest request, HttpServletResponse response, StaffAccount staff)
            throws ServletException, IOException {

        String role = staff.getRole();

        if (role == null) {
            request.setAttribute("error", "Your account does not have a valid role.");
            request.getRequestDispatcher("/view/public/login.jsp").forward(request, response);
            return;
        }

        role = role.trim();

        if (role.equalsIgnoreCase("Receptionist") || role.equalsIgnoreCase("Lễ tân")) {
            RequestDispatcher rd = request.getRequestDispatcher("/view/receptionist/dashboard.jsp");
            rd.forward(request, response);

        } else if (role.equalsIgnoreCase("Manager") || role.equalsIgnoreCase("Quản lý")) {
            RequestDispatcher rd = request.getRequestDispatcher("/view/manager/dashboard.jsp");
            rd.forward(request, response);

        } else if (role.equalsIgnoreCase("Administrator")
                || role.equalsIgnoreCase("Admin")
                || role.equalsIgnoreCase("Quản trị viên")) {
            RequestDispatcher rd = request.getRequestDispatcher("/view/admin/dashboard.jsp");
            rd.forward(request, response);

        } else {
            request.setAttribute("error", "Invalid role: " + role);
            request.getRequestDispatcher("/view/public/login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}