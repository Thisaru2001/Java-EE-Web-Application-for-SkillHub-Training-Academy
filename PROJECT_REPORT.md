# SkillHub Training Academy — Project Report

**Unit:** HF2J — Web Component Development I  
**Project:** SkillHub Training Academy — Training Management Portal  
**Technology:** Java EE Servlet 4.0, JSP 2.3, JSTL, Java 11-compatible source, Maven WAR, Apache Tomcat 9  
**Data storage:** In-memory Java collections only  
**Report status:** Source and WAR build are present. Full deployment and browser-level testing have not been completed in the development environment. See Section 11 and [TESTING.md](./TESTING.md).

> Assessment submission note: replace the evidence placeholders with screenshots captured from the student's own Tomcat deployment. Do not submit a placeholder as if it were a captured screenshot.

## 1. Introduction

SkillHub Training Academy is a role-based training management portal intended to support the core administration of short training programmes. The application provides public student registration and sign-in, course discovery and enrollment, attendance recording, profile pages, and role-specific dashboard routes for administrators, students, and trainers.

The project demonstrates Java EE web components rather than a framework that hides them. Java Servlets receive HTTP requests and perform server-side validation and business operations. JSP pages render the resulting request/session data. A servlet filter protects role-restricted URL families, a servlet-context listener creates and shares application data, and `web.xml` declares the components, URL mappings, welcome page, session timeout, and error routes.

The assessment's no-database restriction is followed. `AppDataStore` keeps application data in Java lists and concurrent maps. This makes the project convenient for a local demonstration but is not durable: all changes are lost on application restart or redeployment.

## 2. Requirements Analysis

### 2.1 Users and permissions

The system defines three roles:

- **Administrator:** views system totals and recent activity; manages course records, student and trainer records, enrollment decisions, attendance reports, and a profile.
- **Student:** registers and signs in; browses and searches active courses; sees course details; requests enrollment; views personal enrollments and attendance; updates a profile.
- **Trainer:** signs in; sees assigned courses and related enrolled students; records attendance for assigned courses; reviews their attendance records; updates a profile.

The authenticated `User` object contains the role and identity used by both the filter and the Servlet layer. A role is never taken from a request parameter. Student-specific data methods take the signed-in student's ID, rather than an ID supplied by the browser.

### 2.2 Functional requirements

The main operations are account registration/authentication, course CRUD, student enrollment, enrollment status changes, trainer course/student views, attendance capture, profile updates, search, validation, friendly errors, and role checks. Important business rules include unique email and username, duplicate enrollment prevention, course capacity, assignment-specific trainer access, and attendance only for an enrolled student.

### 2.3 Quality requirements and constraints

The implementation must be easy to run in Tomcat 9, use Java EE `javax.*` packages, avoid a database, and package as a Maven WAR. Passwords are salted and hashed with PBKDF2. Session idle timeout is configured at 30 minutes by default. The Bootstrap and Bootstrap Icons stylesheet links are CDN references, so the interface needs internet access to fetch those assets unless the user changes the assets to local copies.

## 3. Application Planning

The implementation was organized into these stages:

1. Define Java 11-compatible Maven WAR packaging and Servlet/JSP dependencies.
2. Create model beans and the centralized in-memory store.
3. Initialize sample records and publish shared store/application values from the listener.
4. Implement registration, authentication, sessions, logout, and access filtering.
5. Implement dashboard and management Servlets for each role.
6. Connect JSP forms and tables to their servlet mappings.
7. Build the WAR, execute the available in-memory acceptance checks, record verification limits, and prepare deployment instructions.

The data model intentionally remains simple for a viva explanation. Domain objects are JavaBeans with fields and accessors. `AppDataStore` owns collections, lookups, validation helpers, and operations that need to be atomic in the running process. Servlets coordinate the request and response lifecycle; JSPs render attributes and submit forms.

## 4. Application Architecture

The request path is:

```text
Browser
  -> Tomcat 9 / web.xml URL mapping
  -> AuthenticationFilter for restricted URL families
  -> Servlet (validate, authorize operation, call AppDataStore)
  -> RequestDispatcher forward to JSP or redirect to another route
  -> JSP renders request/session attributes
```

`com.skillhub.model` contains `User`, `Student`, `Trainer`, `Course`, `Enrollment`, and `AttendanceRecord`. `com.skillhub.service.AppDataStore` is the central application data manager. `com.skillhub.servlet` contains request handlers for login, registration, dashboards, courses, enrollments, attendance, student/trainer lists, profile updates, reports, and logout. `com.skillhub.filter.AuthenticationFilter` enforces login and role requirements. `com.skillhub.listener.ApplicationListener` publishes application-wide data at startup. `com.skillhub.util` contains role definitions and password operations.

### 4.1 In-memory data management

`AppDataStore` uses `ArrayList`-based collections for students, trainers, courses, enrollments, and attendance, and `ConcurrentHashMap` indexes for usernames and email addresses. Mutating methods that enforce unique accounts, capacity, or other shared collection invariants are synchronized. Query methods return list copies where appropriate. The context listener stores the singleton store under the ServletContext attribute `appDataStore`.

The sample set includes one administrator, two trainers, five students, six active courses, sample enrollments, and attendance records. The data manager hashes demo passwords when it initializes; the original plaintext is not stored in a `User`.

## 5. Web Application Structure

The relevant Maven tree is:

```text
pom.xml
src/
  main/
    java/com/skillhub/
      filter/AuthenticationFilter.java
      listener/ApplicationListener.java
      model/
      service/AppDataStore.java
      servlet/
      util/
    webapp/
      index.jsp
      login.jsp
      register.jsp
      admin/
      student/
      trainer/
      assets/css/style.css
      assets/js/app.js
      WEB-INF/web.xml
  test/java/com/skillhub/StoreAcceptanceChecks.java
README.md
PROJECT_REPORT.md
TESTING.md
target/skillhub-training-academy.war
```

The deployment descriptor is under `WEB-INF`, so browsers cannot retrieve it directly. Role-specific JSP views are arranged by role for maintainability. The views use JSTL and Expression Language for iteration and displaying request/session attributes rather than implementing domain decisions in JSP scriptlets.

## 6. Servlet Development

The servlet layer uses `HttpServlet` and separates GET page preparation from POST mutations.

- **LoginServlet** validates credentials through the in-memory store, changes the session ID after authentication, stores the user in the session, applies the configured timeout, and redirects by role. Failed credentials are forwarded to the login view with an error attribute.
- **RegisterServlet** checks required values, email format, password confirmation and length, and duplicate accounts before adding a salted-hash student record.
- **CourseServlet** lists/searches courses and handles administrator create/update/delete actions. Course details are available to students for active courses. Course creation checks required inputs, numeric values, and an existing trainer. The admin course page offers edit controls in the course table.
- **EnrollmentServlet** provides the role-specific enrollment list, accepts student enrollment requests, prevents duplicate/full/inactive-course requests, and allows administrators to set only supported enrollment statuses.
- **AttendanceServlet** lists student-specific attendance, administrator records, or trainer-scoped records. Trainers can record only for a course assigned to their account, a valid status, and a student enrolled in that course.
- **StudentServlet** searches and removes student accounts.
- **TrainerServlet** searches trainers, creates a trainer account with a password hash, and removes a trainer only when no course is assigned to them.
- **ProfileServlet** reads and updates the signed-in user's profile.
- **AdminDashboardServlet**, **StudentDashboardServlet**, **TrainerDashboardServlet**, and **ReportsServlet** prepare role-appropriate summary data.
- **LogoutServlet** invalidates the session before returning the browser to login.

Form handlers repeat important validation on the server; HTML `required` attributes are supplementary only. Redirects are used after successful mutations where practical to avoid a browser resubmitting a form. The handlers do not depend on hidden browser fields for the signed-in identity.

## 7. JSP Development

Public pages are `index.jsp`, `login.jsp`, and `register.jsp`. The landing page presents the academy and programme overview. The login page supports username or email, displays validation messages, and includes a show/hide password control. Registration captures name, email, phone, username, password/confirmation, and address.

Role views are located under `admin`, `student`, and `trainer`. They render tables, forms, summary cards, course descriptions, status labels, attendance records, and profile data. A shared CSS file provides the navy navigation rail, blue accent, cards, spacing, and responsive adjustments. A small JavaScript asset handles password visibility; business decisions remain in Java.

The intended reusable output is EL/JSTL rather than Java business logic embedded in JSP. For an internet-independent demonstration, the Bootstrap and icon CDN URLs can be replaced with locally served static copies.

## 8. Web Container Features

### 8.1 ServletContext

`ApplicationListener.contextInitialized` obtains the `ServletContext`, initializes/gets the shared `AppDataStore`, and sets the `appDataStore` and `appName` context attributes. Servlets can retrieve these application-scoped values through the context. The singleton store also supplies the same shared in-memory state for the business operations.

### 8.2 RequestDispatcher

`forward()` is used for server-side view rendering and invalid form submissions. For example, LoginServlet forwards a failed login request to `login.jsp` with an error attribute; dashboard and list Servlets forward prepared attributes to their JSPs. A forward keeps the same request and its attributes and does not require a round-trip redirect.

`FooterServlet` demonstrates an explicit `RequestDispatcher.include()` call to a JSP fragment under `WEB-INF/views`. The public landing page includes that mapped servlet with `<jsp:include>`. The dashboard pages keep role-specific navigation within their own view files.

### 8.3 Servlet Filter

`AuthenticationFilter` is mapped to admin, student, trainer, and shared secured servlet URLs. It checks for an authenticated user in `HttpSession`, redirects anonymous requests to login, and verifies the corresponding role on role-specific URL families. The servlets also verify role/ownership for operations where a shared URL is used, such as enrollment and attendance POSTs. This defense in depth matters because the shared `/enrollments` or `/attendance` route does not itself encode a role in its path.

### 8.4 Servlet Listener

`ApplicationListener` implements `ServletContextListener`. Startup publishes initialized application data and logs a startup message; shutdown logs the lifecycle event. The same listener is registered explicitly in `web.xml`.

### 8.5 web.xml

The Servlet 4.0 descriptor declares the display name, context parameter for session timeout, listener, filter and URL mappings, servlet classes and all active mappings, welcome file, 30-minute session timeout, and friendly error pages. Names are the same as the Java package/class names. The application uses `javax.servlet` APIs appropriate to Tomcat 9, not the `jakarta.servlet` namespace used by Tomcat 10+.

## 9. Session Management

After successful login, `LoginServlet` changes the session identifier to reduce session fixation risk, stores the `User` under the session key `user`, and applies an inactivity interval from the ServletContext setting (1800 seconds by default). The `web.xml` session timeout is also 30 minutes. A production administrator can alter the value, but the servlet-level setting should be kept consistent with the descriptor.

Restricted requests use `getSession(false)` so they do not create empty sessions just to check access. Logout invalidates the existing session. When the idle interval expires, the filter sees no authenticated user and sends the request back to login.

## 10. Security Implementation

Passwords are hashed with PBKDF2-HMAC-SHA-256 via Java security APIs, a fresh 16-byte random salt per account, and 120,000 iterations. Login recomputes the hash and compares decoded bytes using `MessageDigest.isEqual`. User records contain the resulting hash and salt only.

Authorization is based on the server-side role stored in the authenticated session. The filter protects role URL families, while servlet methods validate roles and ownership at sensitive mutation points. A trainer cannot submit attendance for an unassigned course; the store also requires the student/course enrollment. A student attendance lookup is keyed by the session user's ID. Logout invalidates the session. Invalid values are rejected with error/redirect flows rather than accepted based only on browser validation.

This is an educational local demonstration, not a hardened production identity system. Demo accounts are deliberately documented for assessment use. Before real deployment, replace demo credentials, use HTTPS, add CSRF protection to state-changing forms, consider stronger output-escaping conventions consistently, set secure cookie flags at the proxy/container, and add account recovery/lockout and operational monitoring. In-memory data is not suitable for multi-node deployment or durable records.

## 11. Deployment and Testing

### 11.1 Build and deployment status

The application is packaged by Maven as `target/skillhub-training-academy.war`; its WAR packaging configuration and `web.xml` are present. The build was run successfully on 26 October 2026 using the available JDK 21 compiler with `--release 11`. Tomcat 9.0.123 was downloaded from Apache and checksum-verified, extracted, configured on port 18080, and the WAR was copied to its `webapps` directory. The execution environment denied the process-launch request required to start Tomcat, so runtime deployment and browser verification remain incomplete. Port 8080 is occupied by Oracle XML DB, which returns its own 401 response. See [README.md](./README.md) for the prepared local Tomcat command.

### 11.2 Test status

`StoreAcceptanceChecks` was executed against the compiled application classes. Its 14 assertions passed for the data-store operations: sample authentication and incorrect password, adding a student, duplicate email rejection, course create/update/delete, enrollment, duplicate and capacity rejection, attendance recording, and student-scoped attendance lookup.

Those checks are not an HTTP integration suite. They do not execute servlet request/response handling, JSP compilation/rendering, filter redirects, or browser session expiration. Although Tomcat was prepared and the WAR copied into `webapps`, Tomcat could not be started because the execution environment denied process launch. Maven Central DNS also failed during an attempt to obtain embedded-container test dependencies. Therefore the container/browser scenarios are reported as not run or only partially verified, not as passed. See [TESTING.md](./TESTING.md) for the 18-case matrix and actual status.

### 11.3 Evidence to capture during the student's deployment

Add real captures after local deployment. Suggested evidence:

- **Figure 1 — Landing page:** `[Insert screenshot captured from local Tomcat here]`
- **Figure 2 — Successful administrator dashboard:** `[Insert screenshot captured from local Tomcat here]`
- **Figure 3 — Student course listing and enrollment:** `[Insert screenshot captured from local Tomcat here]`
- **Figure 4 — Trainer attendance entry:** `[Insert screenshot captured from local Tomcat here]`
- **Figure 5 — Role restriction or validation message:** `[Insert screenshot captured from local Tomcat here]`
- **Figure 6 — Tomcat deployment/console:** `[Insert screenshot captured from local Tomcat here]`

Do not label these placeholders as executed evidence.

## 12. Operational Maintenance

The application starts from initialized sample data. A restart resets all data, so the administrator should treat the portal as a demonstration system. There is no backup/restore facility because there is no persistence layer. The application logs lifecycle messages through `java.util.logging`; a Tomcat operator should review the server logs for deployment and JSP compilation errors.

For maintenance, keep the Maven compiler target at Java 11 and continue deploying to Tomcat 9 while using `javax.*` APIs. Tomcat 10 is not a drop-in replacement because it changes the Servlet package namespace. Update dependencies through Maven and rebuild/test the WAR when changing the Servlet/JSP container. Keep user/course validation and role rules in Java code and ensure any new route is also protected in the filter and `web.xml`.

If a later assessment version permits a persistent database, the collection-backed store can be replaced behind service interfaces, but that change is intentionally outside this requirement. A persistent production deployment would also need authorization audits, backups, migration planning, and concurrency handling beyond the single-process synchronized in-memory store.

## 13. Conclusion

SkillHub demonstrates the essential Java EE component flow: Servlet mappings, request handling, request forwarding, JSP rendering, filter-based role checks, session authentication, context-scoped shared application state, application lifecycle listener, and WAR deployment configuration. It provides a usable in-memory training workflow without introducing a database or replacing Servlets/JSP with another web framework.

The WAR package has been built and the in-memory acceptance checks have been executed. Full Tomcat deployment and browser-level scenarios still need to be run locally before those outcomes can be claimed. The testing matrix makes this boundary explicit so assessment evidence can be completed truthfully.

## 14. Appendices

### Appendix A — Demonstration credentials

| Role | Username | Password |
|---|---|---|
| Administrator | `admin` | `admin123` |
| Student | `maya` | `student123` |
| Trainer | `ishara` | `trainer123` |

Additional seeded students use `student123`; the second seeded trainer uses `trainer123`.

### Appendix B — Important routes

| Function | Route |
|---|---|
| Landing page | `/` |
| Login | `/login` and `/login.jsp` |
| Registration | `/register` and `/register.jsp` |
| Admin dashboard | `/admin/dashboard` |
| Student dashboard | `/student/dashboard` |
| Trainer dashboard | `/trainer/dashboard` |
| Course listing | `/student/courses` |
| Enrollment | `/enrollments` |
| Attendance | `/attendance` |
| Logout | `/logout` |

### Appendix C — Build artifact

The deployable WAR is generated as `target/skillhub-training-academy.war`. Build and deployment commands are in [README.md](./README.md). The testing evidence and scenarios are in [TESTING.md](./TESTING.md).

### Appendix D — Evidence pages

Reserve the following pages for the student's own deployment evidence:

1. Environment screenshot: IDE JDK and Tomcat 9 configuration.
2. Maven screenshot: successful package output.
3. Tomcat screenshot: deployed application started.
4. Browser screenshot: administrator, student, and trainer workflows.
5. Browser screenshot: invalid login, unauthorized access, or duplicate/capacity validation.
