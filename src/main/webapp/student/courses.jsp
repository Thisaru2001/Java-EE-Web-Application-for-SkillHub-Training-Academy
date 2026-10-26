<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Available Courses</title>
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
                <a href="${pageContext.request.contextPath}/student/dashboard"><i class="bi bi-speedometer2 me-2"></i>Dashboard</a>
                <a href="${pageContext.request.contextPath}/student/courses"><i class="bi bi-journal-bookmark me-2"></i>Available Courses</a>
                <a href="${pageContext.request.contextPath}/student/my-enrollments"><i class="bi bi-card-checklist me-2"></i>My Enrolments</a>
                <a href="${pageContext.request.contextPath}/student/attendance"><i class="bi bi-calendar-check me-2"></i>My Attendance</a>
                <a href="${pageContext.request.contextPath}/student/profile"><i class="bi bi-person-circle me-2"></i>Profile</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Logout</a>
            </nav>
        </aside>
        <main class="col-lg-10 p-4">
            <h2 class="fw-bold mb-4">Available courses</h2>
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
            <form method="get" action="${pageContext.request.contextPath}/student/courses" class="row g-2 mb-4">
                <div class="col-md-9"><input class="form-control" name="search" value="${searchTerm}" placeholder="Search by course name or category"></div>
                <div class="col-md-3"><button class="btn btn-outline-primary w-100" type="submit">Search</button></div>
            </form>
            <div class="row g-4">
                <c:forEach var="course" items="${courses}">
                    <div class="col-md-6 col-xl-4">
                        <div class="card h-100 p-3">
                            <h5 class="fw-bold"><a class="text-decoration-none" href="${pageContext.request.contextPath}/courses?action=details&id=${course.id}">${course.name}</a></h5>
                            <p class="text-secondary mb-2">${course.description}</p>
                            <p class="mb-1"><strong>Category:</strong> ${course.category}</p>
                            <p class="mb-1"><strong>Duration:</strong> ${course.duration}</p>
                            <p class="mb-3"><strong>Fee:</strong> $${course.fee}</p>
                            <form method="post" action="${pageContext.request.contextPath}/enrollments">
                                <input type="hidden" name="action" value="enroll">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <button class="btn btn-primary w-100" type="submit">Enroll Now</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
    </div>
</div>
</body>
</html>
