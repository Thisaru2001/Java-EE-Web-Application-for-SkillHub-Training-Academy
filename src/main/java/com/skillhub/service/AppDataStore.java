package com.skillhub.service;

import com.skillhub.model.AttendanceRecord;
import com.skillhub.model.Course;
import com.skillhub.model.Enrollment;
import com.skillhub.model.Student;
import com.skillhub.model.Trainer;
import com.skillhub.model.User;
import com.skillhub.util.PasswordUtil;
import com.skillhub.util.UserRole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AppDataStore {
    private static final AppDataStore INSTANCE = new AppDataStore();

    private final List<Student> students = new ArrayList<>();
    private final List<Trainer> trainers = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();

    private AppDataStore() {
        initializeSampleData();
    }

    public static AppDataStore getInstance() {
        return INSTANCE;
    }

    private void initializeSampleData() {
        createUser(new Student("STU-1001", "Maya Patel", "maya@student.skillhub.com", "maya", "", "", "+94771234567", "Colombo 03", UserRole.STUDENT, true));
        createUser(new Student("STU-1002", "Nimal Fernando", "nimal@student.skillhub.com", "nimal", "", "", "+94771234568", "Kandy", UserRole.STUDENT, true));
        createUser(new Student("STU-1003", "Kavindi Silva", "kavindi@student.skillhub.com", "kavindi", "", "", "+94771234569", "Galle", UserRole.STUDENT, true));
        createUser(new Student("STU-1004", "Dinesh Perera", "dinesh@student.skillhub.com", "dinesh", "", "", "+94771234570", "Negombo", UserRole.STUDENT, true));
        createUser(new Student("STU-1005", "Sanduni Jayawardena", "sanduni@student.skillhub.com", "sanduni", "", "", "+94771234571", "Matale", UserRole.STUDENT, true));

        createUser(new Trainer("TRN-2001", "Ishara de Silva", "ishara@skillhub.com", "ishara", "", "", "+94771234572", "Colombo", "Java Enterprise", true));
        createUser(new Trainer("TRN-2002", "Ruwan Dias", "ruwan@skillhub.com", "ruwan", "", "", "+94771234573", "Kurunegala", "Cloud & DevOps", true));

        User admin = new User("ADM-3001", "Admin User", "admin@skillhub.com", "admin", "", "", "+94770000001", "Head Office", UserRole.ADMIN, true);
        createUser(admin);

        students.clear();
        trainers.clear();
        List<User> allUsers = new ArrayList<>(usersByUsername.values());
        for (User user : allUsers) {
            if (user.getRole() == UserRole.STUDENT) {
                students.add((Student) user);
            }
            if (user.getRole() == UserRole.TRAINER) {
                trainers.add((Trainer) user);
            }
        }

        String adminSalt = PasswordUtil.generateSalt();
        String adminHash = PasswordUtil.hashPassword("admin123", adminSalt);
        admin.setSalt(adminSalt);
        admin.setPasswordHash(adminHash);

        for (Student student : students) {
            String salt = PasswordUtil.generateSalt();
            student.setSalt(salt);
            student.setPasswordHash(PasswordUtil.hashPassword("student123", salt));
        }

        for (Trainer trainer : trainers) {
            String salt = PasswordUtil.generateSalt();
            trainer.setSalt(salt);
            trainer.setPasswordHash(PasswordUtil.hashPassword("trainer123", salt));
        }

        Course course1 = new Course("CRS-001", "Java EE Fundamentals", "Learn the foundations of Java EE, servlets, JSP, and web applications.", "Programming", "8 Weeks", 150000.0, "TRN-2001", 25, "Active");
        Course course2 = new Course("CRS-002", "Spring Boot Essentials", "Build enterprise-ready web apps with Spring Boot and REST APIs.", "Programming", "10 Weeks", 190000.0, "TRN-2001", 20, "Active");
        Course course3 = new Course("CRS-003", "Frontend Development Mastery", "Master modern frontend interfaces with HTML, CSS, JavaScript and Bootstrap.", "Design", "6 Weeks", 130000.0, "TRN-2002", 30, "Active");
        Course course4 = new Course("CRS-004", "Cloud Computing Basics", "Understand cloud platforms, deployment, and managed infrastructure.", "Cloud", "8 Weeks", 170000.0, "TRN-2002", 22, "Active");
        Course course5 = new Course("CRS-005", "Database Design for Developers", "Learn relational design, normalization, and SQL modelling.", "Data", "7 Weeks", 140000.0, "TRN-2001", 18, "Active");
        Course course6 = new Course("CRS-006", "Project Management for Tech Teams", "Structure projects, manage risk, and deliver value with agile methods.", "Business", "5 Weeks", 110000.0, "TRN-2002", 24, "Active");
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);

        enrollments.add(new Enrollment("ENR-001", "STU-1001", "CRS-001", LocalDate.now().minusDays(12), "Approved"));
        enrollments.add(new Enrollment("ENR-002", "STU-1002", "CRS-003", LocalDate.now().minusDays(9), "Pending"));
        enrollments.add(new Enrollment("ENR-003", "STU-1003", "CRS-001", LocalDate.now().minusDays(8), "Approved"));
        enrollments.add(new Enrollment("ENR-004", "STU-1004", "CRS-004", LocalDate.now().minusDays(5), "Completed"));

        attendanceRecords.add(new AttendanceRecord("ATT-001", "CRS-001", "STU-1001", "TRN-2001", LocalDate.now().minusDays(2), "Present", "Attended fully"));
        attendanceRecords.add(new AttendanceRecord("ATT-002", "CRS-001", "STU-1003", "TRN-2001", LocalDate.now().minusDays(2), "Late", "Arrived 15 minutes late"));
        attendanceRecords.add(new AttendanceRecord("ATT-003", "CRS-003", "STU-1002", "TRN-2002", LocalDate.now().minusDays(1), "Present", "On time"));
        attendanceRecords.add(new AttendanceRecord("ATT-004", "CRS-004", "STU-1004", "TRN-2002", LocalDate.now().minusDays(4), "Absent", "No attendance recorded"));
    }

    public void createUser(User user) {
        if (user == null) {
            return;
        }
        usersByUsername.put(user.getUsername().toLowerCase(), user);
        usersByEmail.put(user.getEmail().toLowerCase(), user);
        if (user.getRole() == UserRole.STUDENT) {
            Student student = (Student) user;
            if (!students.contains(student)) {
                students.add(student);
            }
        } else if (user.getRole() == UserRole.TRAINER) {
            Trainer trainer = (Trainer) user;
            if (!trainers.contains(trainer)) {
                trainers.add(trainer);
            }
        }
    }

    public synchronized List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public synchronized List<Student> getRecentStudents(int limit) {
        return students.stream()
                .sorted(Comparator.comparing(Student::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public synchronized List<Trainer> getTrainers() {
        return new ArrayList<>(trainers);
    }

    public synchronized List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public synchronized List<Enrollment> getEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public synchronized List<AttendanceRecord> getAttendanceRecords() {
        return new ArrayList<>(attendanceRecords);
    }

    public User findUserByUsernameOrEmail(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            return null;
        }
        String normalized = identifier.trim();
        if (usersByUsername.containsKey(normalized.toLowerCase())) {
            return usersByUsername.get(normalized.toLowerCase());
        }
        return usersByEmail.get(normalized.toLowerCase());
    }

    public User authenticateUser(String identifier, String password) {
        User user = findUserByUsernameOrEmail(identifier);
        if (user == null) {
            return null;
        }
        if (!PasswordUtil.verifyPassword(password, user.getPasswordHash(), user.getSalt())) {
            return null;
        }
        return user;
    }

    public boolean existsByEmail(String email) {
        return email != null && usersByEmail.containsKey(email.trim().toLowerCase());
    }

    public boolean existsByUsername(String username) {
        return username != null && usersByUsername.containsKey(username.trim().toLowerCase());
    }

    public synchronized Course findCourseById(String courseId) {
        return courses.stream().filter(course -> course.getId().equalsIgnoreCase(courseId)).findFirst().orElse(null);
    }

    public synchronized Student findStudentById(String studentId) {
        return students.stream().filter(student -> student.getId().equalsIgnoreCase(studentId)).findFirst().orElse(null);
    }

    public synchronized Trainer findTrainerById(String trainerId) {
        return trainers.stream().filter(trainer -> trainer.getId().equalsIgnoreCase(trainerId)).findFirst().orElse(null);
    }

    public synchronized List<Course> getActiveCourses() {
        return courses.stream().filter(course -> "Active".equalsIgnoreCase(course.getStatus())).collect(Collectors.toList());
    }

    public synchronized List<Enrollment> getEnrollmentsForStudent(String studentId) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getStudentId().equalsIgnoreCase(studentId))
                .sorted(Comparator.comparing(Enrollment::getEnrollmentDate).reversed())
                .collect(Collectors.toList());
    }

    public synchronized List<Enrollment> getEnrollmentsForCourse(String courseId) {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getCourseId().equalsIgnoreCase(courseId))
                .collect(Collectors.toList());
    }

    public synchronized List<AttendanceRecord> getAttendanceForCourse(String courseId) {
        return attendanceRecords.stream()
                .filter(record -> record.getCourseId().equalsIgnoreCase(courseId))
                .sorted(Comparator.comparing(AttendanceRecord::getAttendanceDate).reversed())
                .collect(Collectors.toList());
    }

    public synchronized List<AttendanceRecord> getAttendanceForStudent(String studentId) {
        return attendanceRecords.stream()
                .filter(record -> record.getStudentId().equalsIgnoreCase(studentId))
                .sorted(Comparator.comparing(AttendanceRecord::getAttendanceDate).reversed())
                .collect(Collectors.toList());
    }

    public synchronized List<Enrollment> getRecentEnrollments(int limit) {
        return enrollments.stream()
                .sorted(Comparator.comparing(Enrollment::getEnrollmentDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public synchronized List<AttendanceRecord> getRecentAttendance(int limit) {
        return attendanceRecords.stream()
                .sorted(Comparator.comparing(AttendanceRecord::getAttendanceDate).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public synchronized boolean isDuplicateEnrollment(String studentId, String courseId) {
        return enrollments.stream().anyMatch(enrollment ->
                enrollment.getStudentId().equalsIgnoreCase(studentId)
                        && enrollment.getCourseId().equalsIgnoreCase(courseId));
    }

    public synchronized boolean isCourseFull(String courseId) {
        Course course = findCourseById(courseId);
        if (course == null) {
            return true;
        }
        int currentCount = (int) enrollments.stream()
                .filter(enrollment -> enrollment.getCourseId().equalsIgnoreCase(courseId)
                        && !"Rejected".equalsIgnoreCase(enrollment.getStatus()))
                .count();
        return currentCount >= course.getMaxCapacity();
    }

    public synchronized boolean addStudent(Student student) {
        if (student == null || existsByUsername(student.getUsername()) || existsByEmail(student.getEmail())) {
            return false;
        }
        students.add(student);
        usersByUsername.put(student.getUsername().trim().toLowerCase(), student);
        usersByEmail.put(student.getEmail().trim().toLowerCase(), student);
        return true;
    }

    public synchronized boolean addTrainer(Trainer trainer) {
        if (trainer == null || existsByUsername(trainer.getUsername()) || existsByEmail(trainer.getEmail())) {
            return false;
        }
        trainers.add(trainer);
        usersByUsername.put(trainer.getUsername().trim().toLowerCase(), trainer);
        usersByEmail.put(trainer.getEmail().trim().toLowerCase(), trainer);
        return true;
    }

    public synchronized boolean addCourse(Course course) {
        if (course == null || findCourseById(course.getId()) != null
                || findTrainerById(course.getTrainerId()) == null
                || course.getMaxCapacity() < 1 || course.getFee() < 0) {
            return false;
        }
        courses.add(course);
        return true;
    }

    public synchronized void updateCourse(Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equalsIgnoreCase(updatedCourse.getId())) {
                courses.set(i, updatedCourse);
                return;
            }
        }
    }

    public synchronized void deleteCourse(String courseId) {
        courses.removeIf(course -> course.getId().equalsIgnoreCase(courseId));
        enrollments.removeIf(enrollment -> enrollment.getCourseId().equalsIgnoreCase(courseId));
        attendanceRecords.removeIf(record -> record.getCourseId().equalsIgnoreCase(courseId));
    }

    public synchronized boolean addEnrollment(Enrollment enrollment) {
        if (enrollment == null || findStudentById(enrollment.getStudentId()) == null
                || findCourseById(enrollment.getCourseId()) == null
                || isDuplicateEnrollment(enrollment.getStudentId(), enrollment.getCourseId())
                || isCourseFull(enrollment.getCourseId())) {
            return false;
        }
        enrollments.add(enrollment);
        return true;
    }

    public synchronized void deleteEnrollment(String enrollmentId) {
        enrollments.removeIf(enrollment -> enrollment.getId().equalsIgnoreCase(enrollmentId));
    }

    public synchronized boolean addAttendanceRecord(AttendanceRecord attendanceRecord) {
        if (attendanceRecord == null
                || findStudentById(attendanceRecord.getStudentId()) == null
                || findCourseById(attendanceRecord.getCourseId()) == null
                || !isStudentEnrolled(attendanceRecord.getStudentId(), attendanceRecord.getCourseId())
                || !("Present".equals(attendanceRecord.getStatus())
                || "Absent".equals(attendanceRecord.getStatus())
                || "Late".equals(attendanceRecord.getStatus()))) {
            return false;
        }
        attendanceRecords.add(attendanceRecord);
        return true;
    }

    public synchronized void removeStudent(String studentId) {
        students.removeIf(student -> student.getId().equalsIgnoreCase(studentId));
        usersByUsername.values().removeIf(user -> user.getId().equalsIgnoreCase(studentId));
        usersByEmail.values().removeIf(user -> user.getId().equalsIgnoreCase(studentId));
        enrollments.removeIf(enrollment -> enrollment.getStudentId().equalsIgnoreCase(studentId));
        attendanceRecords.removeIf(record -> record.getStudentId().equalsIgnoreCase(studentId));
    }

    public synchronized boolean removeTrainer(String trainerId) {
        if (courses.stream().anyMatch(course -> course.getTrainerId().equalsIgnoreCase(trainerId))) {
            return false;
        }
        trainers.removeIf(trainer -> trainer.getId().equalsIgnoreCase(trainerId));
        usersByUsername.values().removeIf(user -> user.getId().equalsIgnoreCase(trainerId));
        usersByEmail.values().removeIf(user -> user.getId().equalsIgnoreCase(trainerId));
        return true;
    }

    public synchronized List<Course> getCoursesForTrainer(String trainerId) {
        return courses.stream()
                .filter(course -> course.getTrainerId().equalsIgnoreCase(trainerId))
                .collect(Collectors.toList());
    }

    public synchronized List<AttendanceRecord> getAttendanceForTrainer(String trainerId) {
        return attendanceRecords.stream()
                .filter(record -> record.getTrainerId().equalsIgnoreCase(trainerId))
                .sorted(Comparator.comparing(AttendanceRecord::getAttendanceDate).reversed())
                .collect(Collectors.toList());
    }

    public synchronized List<Student> getStudentsForTrainer(String trainerId) {
        List<String> courseIds = getCoursesForTrainer(trainerId).stream()
                .map(Course::getId)
                .collect(Collectors.toList());
        List<String> studentIds = enrollments.stream()
                .filter(enrollment -> courseIds.contains(enrollment.getCourseId()))
                .map(Enrollment::getStudentId)
                .distinct()
                .collect(Collectors.toList());
        return students.stream()
                .filter(student -> studentIds.contains(student.getId()))
                .collect(Collectors.toList());
    }

    public synchronized boolean updateEnrollmentStatus(String enrollmentId, String status) {
        if (!"Pending".equals(status) && !"Approved".equals(status)
                && !"Rejected".equals(status) && !"Completed".equals(status)) {
            return false;
        }
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equalsIgnoreCase(enrollmentId)) {
                enrollment.setStatus(status);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isStudentEnrolled(String studentId, String courseId) {
        return enrollments.stream().anyMatch(enrollment ->
                enrollment.getStudentId().equalsIgnoreCase(studentId)
                        && enrollment.getCourseId().equalsIgnoreCase(courseId)
                        && !"Rejected".equalsIgnoreCase(enrollment.getStatus()));
    }

    public synchronized long countStudents() {
        return students.size();
    }

    public synchronized long countTrainers() {
        return trainers.size();
    }

    public synchronized long countCourses() {
        return courses.size();
    }

    public synchronized long countEnrollments() {
        return enrollments.size();
    }

    public synchronized long countAttendance() {
        return attendanceRecords.size();
    }
}
