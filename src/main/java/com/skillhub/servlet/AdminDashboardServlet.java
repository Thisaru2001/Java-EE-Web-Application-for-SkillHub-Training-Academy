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

public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null || user.getRole() != UserRole.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AppDataStore store = AppDataStore.getInstance();
        request.setAttribute("totalStudents", store.countStudents());
        request.setAttribute("totalTrainers", store.countTrainers());
        request.setAttribute("totalCourses", store.countCourses());
        request.setAttribute("totalEnrollments", store.countEnrollments());
        request.setAttribute("totalAttendance", store.countAttendance());
        request.setAttribute("recentStudents", store.getRecentStudents(5));
        request.setAttribute("recentEnrollments", store.getRecentEnrollments(5));
        request.setAttribute("courseStats", store.getCourses());
        request.setAttribute("currentUser", user);

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
