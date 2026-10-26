<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Student Profile</title>
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
            <h2 class="fw-bold mb-4">Student profile</h2>
            <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
            <div class="card p-4">
                <form method="post" action="${pageContext.request.contextPath}/student/profile">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Full name</label><input class="form-control" name="fullName" value="${user.fullName}"></div>
                        <div class="col-md-6"><label class="form-label">Email</label><input class="form-control" value="${user.email}" disabled></div>
                        <div class="col-md-6"><label class="form-label">Phone</label><input class="form-control" name="phone" value="${user.phone}"></div>
                        <div class="col-md-6"><label class="form-label">Username</label><input class="form-control" value="${user.username}" disabled></div>
                        <div class="col-12"><label class="form-label">Address</label><textarea class="form-control" name="address" rows="3">${user.address}</textarea></div>
                    </div>
                    <button class="btn btn-primary mt-3" type="submit">Update profile</button>
                </form>
            </div>
        </main>
    </div>
</div>
</body>
</html>
