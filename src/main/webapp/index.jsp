<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SkillHub Training Academy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-white border-bottom sticky-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/"><i class="bi bi-mortarboard-fill text-primary me-2"></i>SkillHub Academy</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav ms-auto align-items-lg-center gap-lg-3">
                <li class="nav-item"><a class="nav-link" href="#programmes">Programmes</a></li>
                <li class="nav-item"><a class="nav-link" href="#about">Why SkillHub</a></li>
                <li class="nav-item"><a class="nav-link" href="#benefits">Benefits</a></li>
                <li class="nav-item"><a class="btn btn-outline-primary me-2" href="${pageContext.request.contextPath}/login.jsp">Login</a></li>
                <li class="nav-item"><a class="btn btn-primary" href="${pageContext.request.contextPath}/register.jsp">Register</a></li>
            </ul>
        </div>
    </div>
</nav>

<section class="container py-5">
    <div class="hero row align-items-center">
        <div class="col-lg-7">
            <p class="text-primary fw-semibold mb-2">Future-ready learning</p>
            <h1 class="display-5 fw-bold mb-3">Build technical confidence through practical training.</h1>
            <p class="lead text-secondary mb-4">SkillHub Training Academy delivers career-focused programmes designed to help students, professionals, and teams grow in technology, design, and enterprise development.</p>
            <div class="d-flex gap-3 flex-wrap">
                <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/register.jsp">Join Now</a>
                <a class="btn btn-outline-primary btn-lg" href="${pageContext.request.contextPath}/login.jsp">Student Login</a>
            </div>
        </div>
        <div class="col-lg-5 text-center">
            <img src="https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=900&q=80" alt="Training" class="img-fluid rounded-4 shadow-sm" style="max-height:420px; object-fit:cover; width:100%;">
        </div>
    </div>
</section>

<section id="programmes" class="container py-5">
    <div class="text-center mb-5">
        <p class="text-primary fw-semibold mb-2">Our programmes</p>
        <h2 class="fw-bold">Available training pathways</h2>
    </div>
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card h-100 p-4">
                <div class="icon-box mb-3"><i class="bi bi-code-square"></i></div>
                <h4>Java EE Development</h4>
                <p class="text-secondary">Learn enterprise Java, servlets, JSP, and web components with hands-on projects.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 p-4">
                <div class="icon-box mb-3"><i class="bi bi-cloud-upload"></i></div>
                <h4>Cloud & DevOps</h4>
                <p class="text-secondary">Gain practical knowledge of deployment pipelines, cloud tools, and infrastructure operations.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 p-4">
                <div class="icon-box mb-3"><i class="bi bi-palette"></i></div>
                <h4>UI/UX & Frontend</h4>
                <p class="text-secondary">Master responsive design, modern interfaces, and user-centric product experiences.</p>
            </div>
        </div>
    </div>
</section>

<section id="about" class="container py-5">
    <div class="row align-items-center g-4">
        <div class="col-lg-6">
            <img src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=900&q=80" alt="Academy" class="img-fluid rounded-4 shadow-sm" style="width:100%; height:420px; object-fit:cover;">
        </div>
        <div class="col-lg-6">
            <p class="text-primary fw-semibold mb-2">Why choose SkillHub</p>
            <h2 class="fw-bold mb-3">Training built for real-world outcomes.</h2>
            <p class="text-secondary">Our programmes are designed to combine academic depth with applied skill-building. Students work on portfolio-ready work, guided by expert trainers and practical assessment frameworks.</p>
            <ul class="list-unstyled text-secondary">
                <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Industry-aligned curriculum</li>
                <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Experienced trainers and mentors</li>
                <li class="mb-2"><i class="bi bi-check-circle-fill text-success me-2"></i>Project-based learning experience</li>
            </ul>
        </div>
    </div>
</section>

<section id="benefits" class="container py-5">
    <div class="text-center mb-5">
        <p class="text-primary fw-semibold mb-2">Training benefits</p>
        <h2 class="fw-bold">What you gain with SkillHub</h2>
    </div>
    <div class="row g-4">
        <div class="col-md-3">
            <div class="card h-100 p-4 text-center">
                <div class="icon-box mx-auto mb-3"><i class="bi bi-graph-up-arrow"></i></div>
                <h5>Career growth</h5>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card h-100 p-4 text-center">
                <div class="icon-box mx-auto mb-3"><i class="bi bi-people"></i></div>
                <h5>Mentorship</h5>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card h-100 p-4 text-center">
                <div class="icon-box mx-auto mb-3"><i class="bi bi-laptop"></i></div>
                <h5>Hands-on labs</h5>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card h-100 p-4 text-center">
                <div class="icon-box mx-auto mb-3"><i class="bi bi-award"></i></div>
                <h5>Recognized outcome</h5>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/fragments/footer" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
