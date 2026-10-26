<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trainer Attendance</title>
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
            <h2 class="fw-bold mb-4">Attendance tracking</h2>
            <div class="card p-4">
                <form method="post" action="${pageContext.request.contextPath}/attendance" class="row g-3">
                    <input type="hidden" name="action" value="record">
                    <div class="col-md-3"><label class="form-label">Course</label><select class="form-select" name="courseId">
                        <c:forEach var="course" items="${courses}"><option value="${course.id}">${course.name}</option></c:forEach>
                    </select></div>
                    <div class="col-md-3"><label class="form-label">Student</label><select class="form-select" name="studentId" required>
                        <option value="">Select student</option>
                        <c:forEach var="student" items="${students}"><option value="${student.id}">${student.fullName} (${student.id})</option></c:forEach>
                    </select></div>
                    <div class="col-md-3"><label class="form-label">Status</label><select class="form-select" name="status"><option>Present</option><option>Absent</option><option>Late</option></select></div>
                    <div class="col-md-3"><label class="form-label">Date</label><input class="form-control" type="date" name="attendanceDate"></div>
                    <div class="col-12"><label class="form-label">Notes</label><textarea class="form-control" name="notes"></textarea></div>
                    <div class="col-12"><button class="btn btn-primary" type="submit">Record attendance</button></div>
                </form>
            </div>
            <div class="card p-3 mt-4">
                <table class="table table-hover align-middle mb-0">
                    <thead><tr><th>Course</th><th>Student</th><th>Date</th><th>Status</th></tr></thead>
                    <tbody>
                    <c:forEach var="record" items="${attendanceRecords}">
                        <tr>
                            <td>${record.courseId}</td>
                            <td>${record.studentId}</td>
                            <td>${record.attendanceDate}</td>
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
