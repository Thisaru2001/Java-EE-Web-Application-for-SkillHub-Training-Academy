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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseServlet extends HttpServlet {
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
        String action = request.getParameter("action");
        String searchTerm = request.getParameter("search");
        if ("details".equalsIgnoreCase(action)) {
            String courseId = request.getParameter("id");
            Course course = store.findCourseById(courseId);
            if (course == null || !"Active".equalsIgnoreCase(course.getStatus())
                    || user.getRole() != UserRole.STUDENT) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("course", course);
            request.getRequestDispatcher("/student/course-details.jsp").forward(request, response);
            return;
        }

        if (user.getRole() == UserRole.ADMIN) {
            request.setAttribute("courses", matchingCourses(store.getCourses(), searchTerm));
            request.setAttribute("trainers", store.getTrainers());
            request.setAttribute("searchTerm", searchTerm);
            request.getRequestDispatcher("/admin/courses.jsp").forward(request, response);
            return;
        }
        if (user.getRole() == UserRole.STUDENT) {
            request.setAttribute("courses", matchingCourses(store.getActiveCourses(), searchTerm));
            request.setAttribute("searchTerm", searchTerm);
            request.getRequestDispatcher("/student/courses.jsp").forward(request, response);
            return;
        }

        request.setAttribute("courses", matchingCourses(store.getCoursesForTrainer(user.getId()), searchTerm));
        request.setAttribute("searchTerm", searchTerm);
        request.getRequestDispatcher("/trainer/my-courses.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null || user.getRole() != UserRole.ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = request.getParameter("action");
        AppDataStore store = AppDataStore.getInstance();

        if ("add".equalsIgnoreCase(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String duration = request.getParameter("duration");
            String trainerId = request.getParameter("trainerId");
            String status = request.getParameter("status");
            String feeValue = request.getParameter("fee");
            String capacityValue = request.getParameter("capacity");

            double fee;
            int capacity;
            try {
                fee = feeValue == null || feeValue.trim().isEmpty() ? 0 : Double.parseDouble(feeValue);
                capacity = capacityValue == null || capacityValue.trim().isEmpty()
                        ? 20 : Integer.parseInt(capacityValue);
            } catch (NumberFormatException e) {
                showCourses(request, response, store, "Enter a valid fee and capacity.", null);
                return;
            }

            if (isBlank(name) || isBlank(description) || isBlank(category) || isBlank(trainerId)
                    || !Double.isFinite(fee) || fee < 0 || capacity < 1 || store.findTrainerById(trainerId) == null) {
                showCourses(request, response, store,
                        "Course name, description, category, valid trainer, non-negative fee, and positive capacity are required.", null);
                return;
            }

            Course course = new Course(
                    "CRS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                    name.trim(),
                    description == null ? "" : description.trim(),
                    category.trim(),
                    duration == null ? "6 Weeks" : duration.trim(),
                    fee,
                    trainerId.trim(),
                    capacity,
                    status == null ? "Active" : status
            );
            if (!store.addCourse(course)) {
                showCourses(request, response, store, "The course could not be created; check the trainer and course details.", null);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/admin/courses?success=created");
            return;
        }

        if ("update".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            Course existing = store.findCourseById(id);
            if (existing == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String duration = request.getParameter("duration");
            String trainerId = request.getParameter("trainerId");
            String status = request.getParameter("status");
            try {
                double fee = Double.parseDouble(request.getParameter("fee"));
                int capacity = Integer.parseInt(request.getParameter("capacity"));
                if (isBlank(name) || isBlank(category) || isBlank(trainerId) || !Double.isFinite(fee) || fee < 0 || capacity < 1
                        || store.findTrainerById(trainerId) == null) {
                    showCourses(request, response, store, "Enter valid course details and select an existing trainer.", null);
                    return;
                }
                existing.setName(name.trim());
                existing.setDescription(description == null ? "" : description.trim());
                existing.setCategory(category.trim());
                existing.setDuration(duration == null ? "" : duration.trim());
                existing.setFee(fee);
                existing.setTrainerId(trainerId.trim());
                existing.setMaxCapacity(capacity);
                existing.setStatus("Inactive".equals(status) ? "Inactive" : "Active");
            } catch (NumberFormatException e) {
                showCourses(request, response, store, "Enter a valid fee and capacity.", null);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/admin/courses?success=updated");
            return;
        }

        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            store.deleteCourse(id);
            response.sendRedirect(request.getContextPath() + "/admin/courses");
            return;
        }

        if ("search".equalsIgnoreCase(action)) {
            response.sendRedirect(request.getContextPath() + "/admin/courses?search="
                    + java.net.URLEncoder.encode(request.getParameter("search") == null ? "" : request.getParameter("search"), "UTF-8"));
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void showCourses(HttpServletRequest request, HttpServletResponse response, AppDataStore store,
                             String error, String success) throws ServletException, IOException {
        request.setAttribute("courses", store.getCourses());
        request.setAttribute("trainers", store.getTrainers());
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        request.getRequestDispatcher("/admin/courses.jsp").forward(request, response);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private List<Course> matchingCourses(List<Course> courses, String searchTerm) {
        if (isBlank(searchTerm)) {
            return courses;
        }
        String normalizedSearch = searchTerm.trim().toLowerCase();
        List<Course> matches = new ArrayList<>();
        for (Course course : courses) {
            if (course.getName().toLowerCase().contains(normalizedSearch)
                    || course.getCategory().toLowerCase().contains(normalizedSearch)) {
                matches.add(course);
            }
        }
        return matches;
    }
}
