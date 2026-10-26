<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trainer Management</title>
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
            <h2 class="fw-bold mb-4">Trainer management</h2>
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
            <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
            <div class="card p-3 mb-4">
                <h5 class="fw-bold">Add trainer account</h5>
                <form method="post" action="${pageContext.request.contextPath}/admin/trainers" class="row g-3">
                    <input type="hidden" name="action" value="add">
                    <div class="col-md-4"><input class="form-control" name="fullName" placeholder="Full name" required></div>
                    <div class="col-md-4"><input class="form-control" type="email" name="email" placeholder="Email" required></div>
                    <div class="col-md-4"><input class="form-control" name="phone" placeholder="Phone"></div>
                    <div class="col-md-4"><input class="form-control" name="username" placeholder="Username" required></div>
                    <div class="col-md-4"><input class="form-control" name="specialization" placeholder="Specialization" required></div>
                    <div class="col-md-3"><input class="form-control" type="password" name="password" placeholder="Initial password (8+ chars)" minlength="8" required></div>
                    <div class="col-md-1"><button class="btn btn-primary w-100" type="submit">Add</button></div>
                </form>
            </div>
            <div class="card p-3 mb-4">
                <form method="get" action="${pageContext.request.contextPath}/admin/trainers">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-8">
                            <label class="form-label">Search trainers</label>
                            <input type="text" class="form-control" name="search" value="${searchTerm}" placeholder="Name or email">
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-primary w-100" type="submit">Search</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="card p-3">
                <table class="table table-hover align-middle mb-0">
                    <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Address</th><th>Specialization</th><th>Action</th></tr></thead>
                    <tbody>
                    <c:forEach var="trainer" items="${trainers}">
                        <tr>
                            <td>
                                ${trainer.id}
                                <form id="trainer-${trainer.id}" method="post" action="${pageContext.request.contextPath}/admin/trainers">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${trainer.id}">
                                </form>
                            </td>
                            <td><input form="trainer-${trainer.id}" class="form-control form-control-sm" name="fullName" value="${trainer.fullName}" required></td>
                            <td>${trainer.email}</td>
                            <td><input form="trainer-${trainer.id}" class="form-control form-control-sm" name="phone" value="${trainer.phone}"></td>
                            <td><input form="trainer-${trainer.id}" class="form-control form-control-sm" name="address" value="${trainer.address}"></td>
                            <td><input form="trainer-${trainer.id}" class="form-control form-control-sm" name="specialization" value="${trainer.specialization}"></td>
                            <td>
                                <button form="trainer-${trainer.id}" class="btn btn-sm btn-primary mb-1" type="submit">Save</button>
                                <form method="post" action="${pageContext.request.contextPath}/admin/trainers">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${trainer.id}">
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
