package controller;

import dao.StaffAccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class VerifyCodeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VerifyCodeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyCodeController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.sendRedirect("forgot-password");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String code = request.getParameter("code");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("pendingResetEmail") == null) {
            response.sendRedirect("forgot-password");
            return;
        }

        String email = (String) session.getAttribute("pendingResetEmail");

        StaffAccountDAO dao = new StaffAccountDAO();

        boolean valid = dao.isValidResetCode(email, code);

        if (!valid) {
            request.setAttribute("error", "Invalid or expired code.");
            request.getRequestDispatcher("/view/public/verify-code.jsp").forward(request, response);
            return;
        }

        session.setAttribute("resetEmail", email);
        session.removeAttribute("pendingResetEmail");

        response.sendRedirect("reset-password");
    }

    @Override
    public String getServletInfo() {
        return "Verify Code Controller";
    }
}