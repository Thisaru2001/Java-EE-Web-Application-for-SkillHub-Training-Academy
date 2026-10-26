package com.skillhub.model;

public class Course {
    private String id;
    private String name;
    private String description;
    private String category;
    private String duration;
    private double fee;
    private String trainerId;
    private int maxCapacity;
    private String status;

    public Course() {
    }

    public Course(String id, String name, String description, String category, String duration,
                  double fee, String trainerId, int maxCapacity, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.fee = fee;
        this.trainerId = trainerId;
        this.maxCapacity = maxCapacity;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
