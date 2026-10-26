<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Course Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container py-5">
    <div class="card p-4">
        <h2 class="fw-bold mb-3">${course.name}</h2>
        <p class="text-secondary">${course.description}</p>
        <div class="row g-3">
            <div class="col-md-6"><strong>Category:</strong> ${course.category}</div>
            <div class="col-md-6"><strong>Duration:</strong> ${course.duration}</div>
            <div class="col-md-6"><strong>Fee:</strong> $${course.fee}</div>
            <div class="col-md-6"><strong>Trainer ID:</strong> ${course.trainerId}</div>
            <div class="col-md-6"><strong>Capacity:</strong> ${course.maxCapacity}</div>
            <div class="col-md-6"><strong>Status:</strong> ${course.status}</div>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/enrollments" class="mt-4">
            <input type="hidden" name="action" value="enroll">
            <input type="hidden" name="courseId" value="${course.id}">
            <button class="btn btn-primary" type="submit">Enroll in this course</button>
        </form>
    </div>
</div>
</body>
</html>
