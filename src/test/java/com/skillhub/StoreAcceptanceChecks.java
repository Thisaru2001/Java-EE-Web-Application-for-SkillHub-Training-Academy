package com.skillhub;

import com.skillhub.model.AttendanceRecord;
import com.skillhub.model.Course;
import com.skillhub.model.Enrollment;
import com.skillhub.model.Student;
import com.skillhub.service.AppDataStore;
import com.skillhub.util.PasswordUtil;
import com.skillhub.util.UserRole;

import java.time.LocalDate;
import java.util.UUID;

public final class StoreAcceptanceChecks {
    private StoreAcceptanceChecks() {
    }

    public static void main(String[] args) {
        AppDataStore store = AppDataStore.getInstance();
        check("TC-01", store.authenticateUser("admin", "admin123") != null, "Administrator credentials accepted by auth service");
        check("TC-03", store.authenticateUser("maya", "student123") != null, "Student credentials accepted by auth service");
        check("TC-04", store.authenticateUser("ishara", "trainer123") != null, "Trainer credentials accepted by auth service");
        check("TC-05", store.authenticateUser("admin", "wrong-password") == null, "Incorrect password rejected");

        String salt = PasswordUtil.generateSalt();
        Student first = new Student("TC-STU-1", "Acceptance Student", "tc-student@example.test",
                "tcStudent", PasswordUtil.hashPassword("securepass123", salt), salt,
                "5550101", "Test address", UserRole.STUDENT, true);
        check("TC-02", store.addStudent(first), "New student added to in-memory store");

        Student duplicate = new Student("TC-STU-2", "Duplicate Student", "tc-student@example.test",
                "tcStudent2", first.getPasswordHash(), salt, "5550102", "Test address", UserRole.STUDENT, true);
        check("TC-07", !store.addStudent(duplicate), "Duplicate email rejected by in-memory store");

        Course course = new Course("TC-CRS-" + UUID.randomUUID().toString().substring(0, 8),
                "Acceptance Course", "In-memory lifecycle verification", "Testing", "1 Week",
                0, "TRN-2001", 1, "Active");
        check("TC-08", store.addCourse(course), "Course created");

        course.setName("Acceptance Course Updated");
        course.setCategory("Updated Testing");
        store.updateCourse(course);
        check("TC-09", "Acceptance Course Updated".equals(store.findCourseById(course.getId()).getName()),
                "Course update persisted");

        Enrollment enrollment = new Enrollment("TC-ENR-1", first.getId(), course.getId(), LocalDate.now(), "Pending");
        check("TC-11", store.addEnrollment(enrollment), "Student enrolled in course");
        check("TC-12", !store.addEnrollment(new Enrollment("TC-ENR-2", first.getId(), course.getId(),
                LocalDate.now(), "Pending")), "Duplicate enrollment rejected");

        String secondSalt = PasswordUtil.generateSalt();
        Student second = new Student("TC-STU-3", "Capacity Student", "tc-capacity@example.test",
                "tcCapacity", PasswordUtil.hashPassword("securepass123", secondSalt), secondSalt,
                "5550103", "Test address", UserRole.STUDENT, true);
        check("TC-13", store.addStudent(second)
                        && !store.addEnrollment(new Enrollment("TC-ENR-3", second.getId(), course.getId(),
                        LocalDate.now(), "Pending")),
                "Enrolment rejected after capacity reached");

        AttendanceRecord attendance = new AttendanceRecord("TC-ATT-1", course.getId(), first.getId(),
                "TRN-2001", LocalDate.now(), "Present", "Acceptance check");
        check("TC-14", store.addAttendanceRecord(attendance), "Attendance recorded for enrolled student");
        check("TC-15", store.getAttendanceForStudent(first.getId()).stream()
                        .anyMatch(record -> "TC-ATT-1".equals(record.getId()))
                        && store.getAttendanceForStudent(second.getId()).stream()
                        .noneMatch(record -> "TC-ATT-1".equals(record.getId())),
                "Attendance query returns only the requested student's records");

        store.deleteCourse(course.getId());
        check("TC-10", store.findCourseById(course.getId()) == null
                        && store.getEnrollmentsForStudent(first.getId()).stream()
                        .noneMatch(item -> course.getId().equals(item.getCourseId())),
                "Course deleted and dependent in-memory records removed");
    }

    private static void check(String id, boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(id + " failed: " + description);
        }
        System.out.println("PASS " + id + " - " + description);
    }
}
