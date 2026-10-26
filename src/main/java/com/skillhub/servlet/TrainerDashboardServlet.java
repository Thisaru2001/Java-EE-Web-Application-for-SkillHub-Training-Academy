package com.skillhub.servlet;

import com.skillhub.model.Course;
import com.skillhub.model.User;
import com.skillhub.service.AppDataStore;
import com.skillhub.util.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class TrainerDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null || user.getRole() != UserRole.TRAINER) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AppDataStore store = AppDataStore.getInstance();
        List<Course> assignedCourses = store.getCoursesForTrainer(user.getId());
        request.setAttribute("trainer", user);
        request.setAttribute("assignedCourses", assignedCourses);
        request.setAttribute("courseCount", assignedCourses.size());
        request.setAttribute("studentCount", store.getStudentsForTrainer(user.getId()).size());

        request.getRequestDispatcher("/trainer/dashboard.jsp").forward(request, response);
    }
}
