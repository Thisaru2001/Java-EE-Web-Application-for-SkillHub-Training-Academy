<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Something went wrong</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="card border-0 shadow-sm mx-auto p-4 text-center" style="max-width:560px;">
        <div class="display-1 text-primary">⚠️</div>
        <h2 class="fw-bold mt-3">Page unavailable</h2>
        <p class="text-secondary">The page you requested could not be loaded or access is not permitted.</p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/">Return home</a>
    </div>
</div>
</body>
</html>
