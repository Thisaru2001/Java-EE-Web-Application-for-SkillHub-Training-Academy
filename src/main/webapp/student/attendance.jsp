<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Attendance</title>
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
            <h2 class="fw-bold mb-4">Attendance records</h2>
            <div class="card p-3">
                <table class="table table-hover align-middle mb-0">
                    <thead><tr><th>Course</th><th>Date</th><th>Trainer</th><th>Status</th></tr></thead>
                    <tbody>
                    <c:forEach var="record" items="${attendanceRecords}">
                        <tr>
                            <td>${record.courseId}</td>
                            <td>${record.attendanceDate}</td>
                            <td>${record.trainerId}</td>
                            <td><span class="badge bg-primary-subtle text-primary">${record.status}</span></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>
</body>
</html>
