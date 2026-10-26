package com.skillhub.servlet;

import com.skillhub.model.Trainer;
import com.skillhub.model.User;
import com.skillhub.util.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        request.setAttribute("user", user);
        if (user.getRole() == UserRole.STUDENT) {
            request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
        } else if (user.getRole() == UserRole.TRAINER) {
            request.getRequestDispatcher("/trainer/profile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/admin/profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String fullName = request.getParameter("fullName");
        if (fullName == null || fullName.trim().isEmpty()) {
            request.setAttribute("error", "Full name is required.");
            request.setAttribute("user", user);
            forwardToProfile(request, response, user);
            return;
        }

        user.setFullName(fullName.trim());
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));

        if (user.getRole() == UserRole.TRAINER) {
            ((Trainer) user).setSpecialization(request.getParameter("specialization"));
        }

        request.setAttribute("success", "Profile updated successfully.");
        request.setAttribute("user", user);
        forwardToProfile(request, response, user);
    }

    private void forwardToProfile(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        if (user.getRole() == UserRole.STUDENT) {
            request.getRequestDispatcher("/student/profile.jsp").forward(request, response);
        } else if (user.getRole() == UserRole.TRAINER) {
            request.getRequestDispatcher("/trainer/profile.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/admin/profile.jsp").forward(request, response);
        }
    }
}
