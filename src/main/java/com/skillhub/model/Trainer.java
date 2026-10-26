package com.skillhub.model;

import com.skillhub.util.UserRole;

public class Trainer extends User {
    private String specialization;

    public Trainer() {
        super();
    }

    public Trainer(String id, String fullName, String email, String username, String passwordHash,
                   String salt, String phone, String address, String specialization, boolean active) {
        super(id, fullName, email, username, passwordHash, salt, phone, address, UserRole.TRAINER, active);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
