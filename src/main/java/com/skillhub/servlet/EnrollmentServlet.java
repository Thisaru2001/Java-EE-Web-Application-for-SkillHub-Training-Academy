package com.skillhub.servlet;

import com.skillhub.model.Course;
import com.skillhub.model.Enrollment;
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

public class EnrollmentServlet extends HttpServlet {
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
        if (user.getRole() == UserRole.ADMIN) {
            request.setAttribute("enrollments", store.getEnrollments());
            request.getRequestDispatcher("/admin/enrollments.jsp").forward(request, response);
            return;
        }
        if (user.getRole() == UserRole.STUDENT) {
            request.setAttribute("enrollments", store.getEnrollmentsForStudent(user.getId()));
            request.setAttribute("success", session.getAttribute("flashSuccess"));
            session.removeAttribute("flashSuccess");
            request.getRequestDispatcher("/student/my-enrollments.jsp").forward(request, response);
            return;
        }
        java.util.List<String> assignedCourseIds = store.getCoursesForTrainer(user.getId()).stream()
                .map(Course::getId).collect(java.util.stream.Collectors.toList());
        request.setAttribute("enrollments", store.getEnrollments().stream()
                .filter(enrollment -> assignedCourseIds.contains(enrollment.getCourseId()))
                .collect(java.util.stream.Collectors.toList()));
        request.getRequestDispatcher("/trainer/students.jsp").forward(request, response);
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

        String action = request.getParameter("action");
        AppDataStore store = AppDataStore.getInstance();

        if ("enroll".equalsIgnoreCase(action) && user.getRole() == UserRole.STUDENT) {
            String courseId = request.getParameter("courseId");
            Course course = store.findCourseById(courseId);
            if (course == null) {
                request.setAttribute("error", "The selected course could not be found.");
                request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
                return;
            }
            if (!"Active".equalsIgnoreCase(course.getStatus())) {
                request.setAttribute("error", "This course is not currently open for enrolment.");
                request.setAttribute("courses", store.getActiveCourses());
                request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
                return;
            }
            if (store.isDuplicateEnrollment(user.getId(), courseId)) {
                request.setAttribute("error", "You have already enrolled in this course.");
                request.setAttribute("courses", store.getActiveCourses());
                request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
                return;
            }
            if (store.isCourseFull(courseId)) {
                request.setAttribute("error", "This course has reached its maximum capacity.");
                request.setAttribute("courses", store.getActiveCourses());
                request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
                return;
            }
            Enrollment enrollment = new Enrollment(
                    "ENR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                    user.getId(),
                    courseId,
                    LocalDate.now(),
                    "Pending"
            );
            if (!store.addEnrollment(enrollment)) {
                request.setAttribute("error", "The course is full or you already have an enrolment.");
                request.setAttribute("courses", store.getActiveCourses());
                request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
                return;
            }
            session.setAttribute("flashSuccess", "Course enrollment submitted successfully.");
            response.sendRedirect(request.getContextPath() + "/student/my-enrollments");
            return;
        }

        if ("status".equalsIgnoreCase(action) && user.getRole() == UserRole.ADMIN) {
            String enrollmentId = request.getParameter("enrollmentId");
            String status = request.getParameter("statusValue");
            store.updateEnrollmentStatus(enrollmentId, status);
            response.sendRedirect(request.getContextPath() + "/admin/enrollments");
        }
    }
}
