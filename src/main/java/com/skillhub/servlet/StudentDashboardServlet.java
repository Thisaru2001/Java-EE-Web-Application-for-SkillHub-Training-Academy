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

public class StudentDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null || user.getRole() != UserRole.STUDENT) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AppDataStore store = AppDataStore.getInstance();
        request.setAttribute("student", user);
        request.setAttribute("availableCourses", store.getActiveCourses());
        request.setAttribute("enrolledCourses", store.getEnrollmentsForStudent(user.getId()));
        request.setAttribute("attendanceRecords", store.getAttendanceForStudent(user.getId()));

        request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
    }
}
