package com.skillhub.servlet;

import com.skillhub.model.User;
import com.skillhub.service.AppDataStore;
import com.skillhub.util.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectToDashboard(request, response, user);
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        String password = request.getParameter("password");

        if (identifier == null || identifier.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Username/email and password are required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        User user = AppDataStore.getInstance().authenticateUser(identifier, password);
        if (user == null || !user.isActive()) {
            request.setAttribute("error", "Invalid username/email or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        request.changeSessionId();
        session.setAttribute("user", user);
        String timeout = getServletContext().getInitParameter("session-timeout-seconds");
        session.setMaxInactiveInterval(timeout == null ? 1800 : Integer.parseInt(timeout));

        redirectToDashboard(request, response, user);
    }

    private void redirectToDashboard(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        String contextPath = request.getContextPath();
        if (user.getRole() == UserRole.ADMIN) {
            response.sendRedirect(contextPath + "/admin/dashboard");
            return;
        }
        if (user.getRole() == UserRole.STUDENT) {
            response.sendRedirect(contextPath + "/student/dashboard");
            return;
        }
        response.sendRedirect(contextPath + "/trainer/dashboard");
    }
}
