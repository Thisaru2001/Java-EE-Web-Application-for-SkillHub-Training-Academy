<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enrollment Management</title>
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
            <h2 class="fw-bold mb-4">Enrollment management</h2>
            <div class="card p-3">
                <table class="table align-middle mb-0">
                    <thead><tr><th>ID</th><th>Student</th><th>Course</th><th>Date</th><th>Status</th><th>Action</th></tr></thead>
                    <tbody>
                    <c:forEach var="enrollment" items="${enrollments}">
                        <tr>
                            <td>${enrollment.id}</td>
                            <td>${enrollment.studentId}</td>
                            <td>${enrollment.courseId}</td>
                            <td>${enrollment.enrollmentDate}</td>
                            <td><span class="badge bg-primary-subtle text-primary">${enrollment.status}</span></td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/enrollments">
                                    <input type="hidden" name="action" value="status">
                                    <input type="hidden" name="enrollmentId" value="${enrollment.id}">
                                    <select class="form-select form-select-sm d-inline-block w-auto" name="statusValue">
                                        <option>Pending</option>
                                        <option>Approved</option>
                                        <option>Rejected</option>
                                        <option>Completed</option>
                                    </select>
                                    <button class="btn btn-sm btn-primary" type="submit">Update</button>
                                </form>
                            </td>
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
