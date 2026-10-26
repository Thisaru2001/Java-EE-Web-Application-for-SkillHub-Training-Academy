package com.skillhub.servlet;

import com.skillhub.model.Student;
import com.skillhub.model.User;
import com.skillhub.service.AppDataStore;
import com.skillhub.util.PasswordUtil;
import com.skillhub.util.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");

        if (fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty() || username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()
                || address == null || address.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            request.setAttribute("error", "Enter a valid email address.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        AppDataStore dataStore = AppDataStore.getInstance();
        if (dataStore.existsByEmail(email) || dataStore.existsByUsername(username)) {
            request.setAttribute("error", "An account already exists with that email address or username.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (password.length() < 8) {
            request.setAttribute("error", "Password must be at least 8 characters.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(password, salt);
        Student student = new Student(
                "STU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                fullName.trim(),
                email.trim(),
                username.trim(),
                hashedPassword,
                salt,
                phone.trim(),
                address.trim(),
                UserRole.STUDENT,
                true
        );

        if (!dataStore.addStudent(student)) {
            request.setAttribute("error", "An account already exists with that email address or username.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        request.setAttribute("success", "Registration successful. Please login.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
