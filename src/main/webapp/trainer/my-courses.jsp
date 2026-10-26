<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Courses</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <aside class="col-lg-2 sidebar p-3">
            <h4 class="mb-4"><i class="bi bi-mortarboard-fill me-2"></i>SkillHub</h4>
            <nav>
                <a href="${pageContext.request.contextPath}/trainer/dashboard"><i class="bi bi-speedometer2 me-2"></i>Dashboard</a>
                <a href="${pageContext.request.contextPath}/trainer/my-courses"><i class="bi bi-journal-bookmark me-2"></i>My Courses</a>
                <a href="${pageContext.request.contextPath}/trainer/students"><i class="bi bi-people me-2"></i>Students</a>
                <a href="${pageContext.request.contextPath}/trainer/attendance"><i class="bi bi-calendar-check me-2"></i>Attendance</a>
                <a href="${pageContext.request.contextPath}/trainer/profile"><i class="bi bi-person-circle me-2"></i>Profile</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Logout</a>
            </nav>
        </aside>
        <main class="col-lg-10 p-4">
            <h2 class="fw-bold mb-4">My courses</h2>
            <div class="row g-4">
                <c:forEach var="course" items="${courses}">
                    <div class="col-md-6">
                        <div class="card p-3 h-100">
                            <h5 class="fw-bold">${course.name}</h5>
                            <p class="text-secondary">${course.description}</p>
                            <p class="mb-1"><strong>Category:</strong> ${course.category}</p>
                            <p class="mb-1"><strong>Duration:</strong> ${course.duration}</p>
                            <p class="mb-0"><strong>Status:</strong> ${course.status}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
</div>
</body>
</html>
