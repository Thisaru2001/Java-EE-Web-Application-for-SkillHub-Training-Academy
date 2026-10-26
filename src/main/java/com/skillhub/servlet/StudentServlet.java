package com.skillhub.servlet;

import com.skillhub.model.Student;
import com.skillhub.model.User;
import com.skillhub.service.AppDataStore;
import com.skillhub.util.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentServlet extends HttpServlet {
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
        String searchTerm = request.getParameter("search");
        List<Student> students = new ArrayList<>();
        for (Student student : store.getStudents()) {
            if (searchTerm == null || searchTerm.trim().isEmpty()
                    || student.getFullName().toLowerCase().contains(searchTerm.toLowerCase())
                    || student.getEmail().toLowerCase().contains(searchTerm.toLowerCase())) {
                students.add(student);
            }
        }

        request.setAttribute("students", students);
        request.setAttribute("searchTerm", searchTerm);
        request.getRequestDispatcher("/admin/students.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        AppDataStore store = AppDataStore.getInstance();

        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            store.removeStudent(id);
            response.sendRedirect(request.getContextPath() + "/admin/students");
            return;
        }
        if ("update".equalsIgnoreCase(action)) {
            Student student = store.findStudentById(request.getParameter("id"));
            if (student == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String fullName = request.getParameter("fullName");
            if (fullName == null || fullName.trim().isEmpty()) {
                request.setAttribute("error", "Student name is required.");
            } else {
                student.setFullName(fullName.trim());
                student.setPhone(request.getParameter("phone"));
                student.setAddress(request.getParameter("address"));
                request.setAttribute("success", "Student details updated.");
            }
            request.setAttribute("students", store.getStudents());
            request.getRequestDispatcher("/admin/students.jsp").forward(request, response);
        }
    }
}
