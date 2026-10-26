package com.skillhub.filter;

import com.skillhub.model.User;
import com.skillhub.util.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (uri.endsWith("/login") || uri.endsWith("/register") || uri.endsWith("/index.jsp")
                || uri.endsWith("/login.jsp") || uri.endsWith("/register.jsp")
                || uri.contains("/assets/") || uri.contains("/css/") || uri.contains("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;

        if (uri.contains("/admin/")) {
            if (user == null) {
                httpResponse.sendRedirect(contextPath + "/login.jsp?error=Please login first");
                return;
            }
            if (user.getRole() != UserRole.ADMIN) {
                httpResponse.sendRedirect(contextPath + "/login.jsp?error=Unauthorized access to admin area");
                return;
            }
        }

        if (uri.contains("/student/")) {
            if (user == null) {
                httpResponse.sendRedirect(contextPath + "/login.jsp?error=Please login first");
                return;
            }
            if (user.getRole() != UserRole.STUDENT) {
                httpResponse.sendRedirect(contextPath + "/login.jsp?error=Student access required");
                return;
            }
        }

        if (uri.contains("/trainer/")) {
            if (user == null) {
                httpResponse.sendRedirect(contextPath + "/login.jsp?error=Please login first");
                return;
            }
            if (user.getRole() != UserRole.TRAINER) {
                httpResponse.sendRedirect(contextPath + "/login.jsp?error=Trainer access required");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
