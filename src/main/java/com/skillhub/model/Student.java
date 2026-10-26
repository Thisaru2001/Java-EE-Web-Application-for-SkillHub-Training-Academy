package com.skillhub.model;

import com.skillhub.util.UserRole;

public class Student extends User {
    public Student() {
        super();
    }

    public Student(String id, String fullName, String email, String username, String passwordHash,
                   String salt, String phone, String address, UserRole role, boolean active) {
        super(id, fullName, email, username, passwordHash, salt, phone, address, role, active);
    }
}
