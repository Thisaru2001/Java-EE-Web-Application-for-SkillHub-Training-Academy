<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
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
                <a href="${pageContext.request.contextPath}/admin/dashboard"><i class="bi bi-speedometer2 me-2"></i>Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/students"><i class="bi bi-people me-2"></i>Students</a>
                <a href="${pageContext.request.contextPath}/admin/trainers"><i class="bi bi-person-badge me-2"></i>Trainers</a>
                <a href="${pageContext.request.contextPath}/admin/courses"><i class="bi bi-journal-bookmark me-2"></i>Courses</a>
                <a href="${pageContext.request.contextPath}/admin/enrollments"><i class="bi bi-card-checklist me-2"></i>Enrollments</a>
                <a href="${pageContext.request.contextPath}/admin/attendance"><i class="bi bi-calendar-check me-2"></i>Attendance</a>
                <a href="${pageContext.request.contextPath}/admin/reports"><i class="bi bi-bar-chart me-2"></i>Reports</a>
                <a href="${pageContext.request.contextPath}/profile"><i class="bi bi-person-circle me-2"></i>Profile</a>
                <a href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Logout</a>
            </nav>
        </aside>
        <main class="col-lg-10 p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="fw-bold mb-0">Admin dashboard</h2>
                    <p class="text-secondary mb-0">Welcome, ${sessionScope.user.fullName}</p>
                </div>
                <span class="badge bg-primary-subtle text-primary">Administrator</span>
            </div>
            <div class="row g-4 mb-4">
                <div class="col-md-6 col-xl-4"><div class="card stat-card p-3"><div class="d-flex justify-content-between"><div><p class="text-secondary mb-1">Students</p><h3>${totalStudents}</h3></div><i class="bi bi-people fs-3 text-primary"></i></div></div></div>
                <div class="col-md-6 col-xl-4"><div class="card stat-card p-3"><div class="d-flex justify-content-between"><div><p class="text-secondary mb-1">Trainers</p><h3>${totalTrainers}</h3></div><i class="bi bi-person-badge fs-3 text-primary"></i></div></div></div>
                <div class="col-md-6 col-xl-4"><div class="card stat-card p-3"><div class="d-flex justify-content-between"><div><p class="text-secondary mb-1">Courses</p><h3>${totalCourses}</h3></div><i class="bi bi-journal-bookmark fs-3 text-primary"></i></div></div></div>
                <div class="col-md-6 col-xl-4"><div class="card stat-card p-3"><div class="d-flex justify-content-between"><div><p class="text-secondary mb-1">Enrollments</p><h3>${totalEnrollments}</h3></div><i class="bi bi-card-checklist fs-3 text-primary"></i></div></div></div>
                <div class="col-md-6 col-xl-4"><div class="card stat-card p-3"><div class="d-flex justify-content-between"><div><p class="text-secondary mb-1">Attendance</p><h3>${totalAttendance}</h3></div><i class="bi bi-calendar-check fs-3 text-primary"></i></div></div></div>
                <div class="col-md-6 col-xl-4"><div class="card stat-card p-3"><div class="d-flex justify-content-between"><div><p class="text-secondary mb-1">Active Courses</p><h3>${totalCourses}</h3></div><i class="bi bi-check2-circle fs-3 text-primary"></i></div></div></div>
            </div>

            <div class="row g-4">
                <div class="col-xl-6">
                    <div class="card p-3">
                        <h5 class="fw-bold mb-3">Recent student registrations</h5>
                        <table class="table table-hover align-middle mb-0">
                            <thead><tr><th>Name</th><th>Email</th><th>Username</th></tr></thead>
                            <tbody>
                            <c:forEach var="student" items="${recentStudents}">
                                <tr>
                                    <td>${student.fullName}</td>
                                    <td>${student.email}</td>
                                    <td>${student.username}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-xl-6">
                    <div class="card p-3">
                        <h5 class="fw-bold mb-3">Recent enrolments</h5>
                        <table class="table table-hover align-middle mb-0">
                            <thead><tr><th>Course</th><th>Student</th><th>Status</th></tr></thead>
                            <tbody>
                            <c:forEach var="enrollment" items="${recentEnrollments}">
                                <tr>
                                    <td>${enrollment.courseId}</td>
                                    <td>${enrollment.studentId}</td>
                                    <td><span class="badge bg-primary-subtle text-primary">${enrollment.status}</span></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-12">
                    <div class="card p-3">
                        <h5 class="fw-bold mb-3">Course statistics</h5>
                        <table class="table table-hover align-middle mb-0">
                            <thead><tr><th>Course</th><th>Category</th><th>Capacity</th><th>Fee</th></tr></thead>
                            <tbody>
                            <c:forEach var="course" items="${courseStats}">
                                <tr>
                                    <td>${course.name}</td>
                                    <td>${course.category}</td>
                                    <td>${course.maxCapacity}</td>
                                    <td>$${course.fee}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
</body>
</html>
