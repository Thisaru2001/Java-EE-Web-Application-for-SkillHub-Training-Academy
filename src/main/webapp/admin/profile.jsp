<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrator Profile</title>
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
            <h2 class="fw-bold mb-4">Administrator profile</h2>
            <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
            <div class="card p-4">
                <form method="post" action="${pageContext.request.contextPath}/profile">
                    <div class="row g-3">
                        <div class="col-md-6"><label class="form-label">Full name</label><input class="form-control" name="fullName" value="${user.fullName}" required></div>
                        <div class="col-md-6"><label class="form-label">Email</label><input class="form-control" value="${user.email}" disabled></div>
                        <div class="col-md-6"><label class="form-label">Phone</label><input class="form-control" name="phone" value="${user.phone}"></div>
                        <div class="col-md-6"><label class="form-label">Username</label><input class="form-control" value="${user.username}" disabled></div>
                        <div class="col-12"><label class="form-label">Address</label><textarea class="form-control" name="address">${user.address}</textarea></div>
                    </div>
                    <button class="btn btn-primary mt-3" type="submit">Update profile</button>
                </form>
            </div>
        </main>
    </div>
</div>
</body>
</html>
