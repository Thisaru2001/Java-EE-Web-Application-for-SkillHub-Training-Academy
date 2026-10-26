package com.skillhub.servlet;

import com.skillhub.model.AttendanceRecord;
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
import java.time.LocalDate;
import java.util.UUID;

public class AttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        AppDataStore store = AppDataStore.getInstance();
        if (user.getRole() == UserRole.TRAINER) {
            request.setAttribute("attendanceRecords", store.getAttendanceForTrainer(user.getId()));
            request.setAttribute("courses", store.getCoursesForTrainer(user.getId()));
            request.setAttribute("students", store.getStudentsForTrainer(user.getId()));
            request.getRequestDispatcher("/trainer/attendance.jsp").forward(request, response);
            return;
        }
        if (user.getRole() == UserRole.STUDENT) {
            request.setAttribute("attendanceRecords", store.getAttendanceForStudent(user.getId()));
            request.getRequestDispatcher("/student/attendance.jsp").forward(request, response);
            return;
        }

        request.setAttribute("attendanceRecords", store.getAttendanceRecords());
        request.getRequestDispatcher("/admin/attendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        if (user == null || user.getRole() != UserRole.TRAINER) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");
        String dateValue = request.getParameter("attendanceDate");

        Course course = AppDataStore.getInstance().findCourseById(courseId);
        if (course == null || !course.getTrainerId().equalsIgnoreCase(user.getId())
                || studentId == null || studentId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/trainer/attendance");
            return;
        }

        if (!"Present".equals(status) && !"Absent".equals(status) && !"Late".equals(status)) {
            response.sendRedirect(request.getContextPath() + "/trainer/attendance?error=invalid-status");
            return;
        }

        LocalDate attendanceDate;
        try {
            attendanceDate = dateValue == null || dateValue.isEmpty() ? LocalDate.now() : LocalDate.parse(dateValue);
        } catch (java.time.format.DateTimeParseException e) {
            response.sendRedirect(request.getContextPath() + "/trainer/attendance?error=invalid-date");
            return;
        }

        AttendanceRecord record = new AttendanceRecord(
                "ATT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                courseId,
                studentId,
                user.getId(),
                attendanceDate,
                status,
                notes == null ? "" : notes
        );
        if (!AppDataStore.getInstance().addAttendanceRecord(record)) {
            response.sendRedirect(request.getContextPath() + "/trainer/attendance?error=student-not-enrolled");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/trainer/attendance");
    }
}
