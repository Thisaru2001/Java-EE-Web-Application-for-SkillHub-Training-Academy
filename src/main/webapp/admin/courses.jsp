<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Course Management</title>
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
            <h2 class="fw-bold mb-4">Course management</h2>
            <c:if test="${param.success eq 'created'}"><div class="alert alert-success">Course created successfully.</div></c:if>
            <c:if test="${param.success eq 'updated'}"><div class="alert alert-success">Course updated successfully.</div></c:if>
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
            <div class="card p-3 mb-4">
                <form method="get" action="${pageContext.request.contextPath}/admin/courses" class="row g-2">
                    <div class="col-md-9"><input class="form-control" name="search" value="${searchTerm}" placeholder="Search course name or category"></div>
                    <div class="col-md-3"><button class="btn btn-outline-primary w-100" type="submit">Search</button></div>
                </form>
            </div>
            <div class="card p-3 mb-4">
                <form method="post" action="${pageContext.request.contextPath}/admin/courses">
                    <input type="hidden" name="action" value="add">
                    <div class="row g-3">
                        <div class="col-md-4"><input class="form-control" name="name" placeholder="Course name" required></div>
                        <div class="col-md-3"><input class="form-control" name="category" placeholder="Category" required></div>
                        <div class="col-md-2"><input class="form-control" name="duration" placeholder="Duration" required></div>
                        <div class="col-md-2"><input class="form-control" name="fee" type="number" min="0" step="0.01" placeholder="Fee" required></div>
                        <div class="col-md-1"><button class="btn btn-primary w-100" type="submit">Add</button></div>
                    </div>
                    <div class="row g-3 mt-1">
                        <div class="col-md-5"><input class="form-control" name="description" placeholder="Description" required></div>
                        <div class="col-md-3"><select class="form-select" name="trainerId" required>
                            <option value="">Select trainer</option>
                            <c:forEach var="trainer" items="${trainers}"><option value="${trainer.id}">${trainer.fullName} (${trainer.id})</option></c:forEach>
                        </select></div>
                        <div class="col-md-2"><input class="form-control" name="capacity" type="number" placeholder="Capacity" min="1" required></div>
                        <div class="col-md-2"><select class="form-select" name="status"><option>Active</option><option>Inactive</option></select></div>
                    </div>
                </form>
            </div>
            <div class="card p-3">
                <table class="table table-hover align-middle mb-0">
                    <thead><tr><th>ID</th><th>Name</th><th>Category</th><th>Trainer</th><th>Fee</th><th>Capacity</th><th>Status</th><th>Actions</th></tr></thead>
                    <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td>
                                ${course.id}
                                <form id="course-${course.id}" method="post" action="${pageContext.request.contextPath}/admin/courses">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${course.id}">
                                </form>
                            </td>
                            <td>
                                <input form="course-${course.id}" class="form-control form-control-sm" name="name" value="${course.name}" required>
                                <input form="course-${course.id}" class="form-control form-control-sm mt-1" name="description" value="${course.description}" aria-label="Course description">
                                <input form="course-${course.id}" class="form-control form-control-sm mt-1" name="duration" value="${course.duration}" aria-label="Course duration">
                            </td>
                            <td><input form="course-${course.id}" class="form-control form-control-sm" name="category" value="${course.category}" required></td>
                            <td>
                                <select form="course-${course.id}" class="form-select form-select-sm" name="trainerId" required>
                                    <c:forEach var="trainer" items="${trainers}">
                                        <option value="${trainer.id}" ${trainer.id eq course.trainerId ? 'selected' : ''}>${trainer.fullName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input form="course-${course.id}" class="form-control form-control-sm" type="number" min="0" step="0.01" name="fee" value="${course.fee}" required></td>
                            <td><input form="course-${course.id}" class="form-control form-control-sm" type="number" min="1" name="capacity" value="${course.maxCapacity}" required></td>
                            <td><select form="course-${course.id}" class="form-select form-select-sm" name="status"><option ${course.status eq 'Active' ? 'selected' : ''}>Active</option><option ${course.status eq 'Inactive' ? 'selected' : ''}>Inactive</option></select></td>
                            <td>
                                <button form="course-${course.id}" class="btn btn-sm btn-primary" type="submit">Save</button>
                                <form method="post" action="${pageContext.request.contextPath}/admin/courses">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${course.id}">
                                    <button class="btn btn-sm btn-outline-danger" type="submit">Delete</button>
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
