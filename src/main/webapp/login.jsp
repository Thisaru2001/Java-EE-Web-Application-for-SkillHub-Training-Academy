<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | SkillHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container">
    <div class="card auth-card shadow-sm p-4">
        <div class="text-center mb-4">
            <div class="icon-box mx-auto mb-3"><i class="bi bi-mortarboard-fill"></i></div>
            <h2 class="fw-bold mb-1">Welcome back</h2>
            <p class="text-secondary mb-0">Sign in to your SkillHub account</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">${param.error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label class="form-label">Email or Username</label>
                <input type="text" name="identifier" class="form-control" placeholder="Enter email or username" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <div class="input-group">
                    <input id="passwordInput" type="password" name="password" class="form-control" placeholder="Enter password" required>
                    <button type="button" class="btn btn-outline-secondary" data-toggle-password aria-label="Show password">
                        <i class="bi bi-eye"></i>
                    </button>
                </div>
            </div>
            <button class="btn btn-primary w-100 mb-3" type="submit">Login</button>
        </form>

        <div class="text-center">
            <span class="text-secondary">New here?</span>
            <a href="${pageContext.request.contextPath}/register.jsp" class="text-primary text-decoration-none fw-semibold">Create an account</a>
        </div>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/" class="text-decoration-none text-secondary">Back to home</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>
