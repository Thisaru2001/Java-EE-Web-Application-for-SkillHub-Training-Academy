package com.skillhub.servlet;

import com.skillhub.model.Trainer;
import com.skillhub.model.User;
import com.skillhub.service.AppDataStore;
import com.skillhub.util.PasswordUtil;
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

public class TrainerServlet extends HttpServlet {
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
        List<Trainer> trainers = new ArrayList<>();
        for (Trainer trainer : store.getTrainers()) {
            if (searchTerm == null || searchTerm.trim().isEmpty()
                    || trainer.getFullName().toLowerCase().contains(searchTerm.toLowerCase())
                    || trainer.getEmail().toLowerCase().contains(searchTerm.toLowerCase())) {
                trainers.add(trainer);
            }
        }

        request.setAttribute("trainers", trainers);
        request.setAttribute("searchTerm", searchTerm);
        request.getRequestDispatcher("/admin/trainers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        AppDataStore store = AppDataStore.getInstance();

        if ("delete".equalsIgnoreCase(action)) {
            String id = request.getParameter("id");
            if (!store.removeTrainer(id)) {
                request.setAttribute("error", "Remove or reassign this trainer's courses before deleting the account.");
                request.setAttribute("trainers", store.getTrainers());
                request.getRequestDispatcher("/admin/trainers.jsp").forward(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath() + "/admin/trainers");
            return;
        }

        if ("add".equalsIgnoreCase(action)) {
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            String specialization = request.getParameter("specialization");
            String password = request.getParameter("password");
            if (isBlank(fullName) || isBlank(email) || isBlank(username) || isBlank(password)
                    || isBlank(specialization) || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                    || password.length() < 8) {
                request.setAttribute("error", "Complete all required fields with a valid email and password (8+ characters).");
            } else {
                String salt = PasswordUtil.generateSalt();
                Trainer trainer = new Trainer("TRN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                        fullName.trim(), email.trim(), username.trim(),
                        PasswordUtil.hashPassword(password, salt), salt, phone == null ? "" : phone.trim(),
                        "", specialization.trim(), true);
                if (!store.addTrainer(trainer)) {
                    request.setAttribute("error", "A trainer account already exists with that email or username.");
                } else {
                    request.setAttribute("success", "Trainer account created.");
                }
            }
            request.setAttribute("trainers", store.getTrainers());
            request.getRequestDispatcher("/admin/trainers.jsp").forward(request, response);
            return;
        }

        if ("update".equalsIgnoreCase(action)) {
            Trainer trainer = store.findTrainerById(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            if (trainer == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (isBlank(fullName)) {
                request.setAttribute("error", "Trainer name is required.");
            } else {
                trainer.setFullName(fullName.trim());
                trainer.setPhone(request.getParameter("phone"));
                trainer.setAddress(request.getParameter("address"));
                trainer.setSpecialization(request.getParameter("specialization"));
                request.setAttribute("success", "Trainer details updated.");
            }
            request.setAttribute("trainers", store.getTrainers());
            request.getRequestDispatcher("/admin/trainers.jsp").forward(request, response);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
