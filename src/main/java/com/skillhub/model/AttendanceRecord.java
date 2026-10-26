package com.skillhub.model;

import java.time.LocalDate;

public class AttendanceRecord {
    private String id;
    private String courseId;
    private String studentId;
    private String trainerId;
    private LocalDate attendanceDate;
    private String status;
    private String notes;

    public AttendanceRecord() {
    }

    public AttendanceRecord(String id, String courseId, String studentId, String trainerId,
                           LocalDate attendanceDate, String status, String notes) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.trainerId = trainerId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
