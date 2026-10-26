# SkillHub Training Academy

HF2J Web Component Development I — Java EE training management portal.

This Maven WAR uses Java 11-compatible source, Java Servlets 4.0, JSP 2.3, JSTL, and Apache Tomcat 9. All academy records are in memory; no database is used. Students, trainers, courses, enrollments, and attendance are reset when the web application restarts.

## Demo accounts

| Role | Username | Password |
|---|---|---|
| Administrator | `admin` | `admin123` |
| Student | `maya` | `student123` |
| Trainer | `ishara` | `trainer123` |

These are demonstration credentials, not production credentials. Change them before any use beyond a local assessment demo.

## Requirements

- JDK 11 (or a newer JDK capable of compiling with `--release 11`)
- Apache Maven 3.8+
- Apache Tomcat 9.0.x
- IntelliJ IDEA or Eclipse (optional)

## Open in an IDE

### IntelliJ IDEA

1. Choose **File → Open** and select this project's `pom.xml` or project folder.
2. Import it as a Maven project.
3. Under **Project Structure → Project**, select JDK 11 as the project SDK and language level.
4. Allow Maven to resolve dependencies.

### Eclipse

1. Choose **File → Import → Maven → Existing Maven Projects**.
2. Select this project folder and finish the import.
3. Under **Preferences → Java → Installed JREs**, add/select JDK 11.
4. Use **Maven → Update Project** if Eclipse reports unresolved dependencies.

The project was built in the supplied environment with JDK 21 using Maven compiler `--release 11`; it was not compiled on an installed JDK 11.

## Build the WAR

From the project root:

```powershell
mvn clean package
```

The deployable artifact is:

```text
target/skillhub-training-academy.war
```

The build uses Servlet API 4.0.1 and JSP API 2.3.3 with `provided` scope because Tomcat supplies those APIs. JSTL is packaged with the application. No database driver or database setup is required.

## Deploy to Tomcat 9

1. Install and configure a local Tomcat 9 instance in the IDE, or download and extract Apache Tomcat 9.
2. Build the WAR using `mvn clean package`.
3. Copy `target/skillhub-training-academy.war` into Tomcat's `webapps` directory. Tomcat will expand it using the `skillhub-training-academy` context name.
4. Ensure Tomcat's HTTP connector port is free. If another service uses 8080, change the active `<Connector port="8080"` in `conf\server.xml` (for example, to `18080`).
5. Start Tomcat using the IDE's server control or run `bin\startup.bat` from the Tomcat installation. For the Tomcat copy prepared for this project, open PowerShell and run:

   ```powershell
   $env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
   $env:CATALINA_HOME = Join-Path $env:LOCALAPPDATA 'SkillHubDemo\apache-tomcat-9.0.123'
   $env:CATALINA_BASE = $env:CATALINA_HOME
   & "$env:CATALINA_HOME\bin\startup.bat"
   ```

   This Tomcat instance uses port `18080`, because port 8080 is occupied by Oracle XML DB on this machine.
6. Open:

   ```text
   http://localhost:18080/skillhub-training-academy/
   ```

7. Use one of the demo accounts above. New student accounts can be created from the public registration page.

To stop the prepared Tomcat instance, run `& "$env:CATALINA_HOME\bin\shutdown.bat"` in the same PowerShell session. For another Tomcat installation, use that installation's `bin\shutdown.bat`.

## Functional areas

- Public academy landing page, student registration, and login.
- Role-aware admin, student, and trainer dashboard flows.
- Course creation, listing, editing, deletion, and search.
- Student course enrollment with duplicate and capacity checks.
- Enrollment status management.
- Trainer attendance recording restricted to courses assigned to that trainer and students enrolled in those courses.
- Student-only personal attendance view.
- Profile update, student/trainer search, and account deletion/creation interfaces.
- PBKDF2 salted password hashes, role checks, logout/session invalidation, and inactivity timeout.

## Testing

The dependency-free in-memory acceptance checks are in `src/test/java/com/skillhub/StoreAcceptanceChecks.java`. After building, run them with:

```powershell
java -cp "target\classes;target\test-classes" com.skillhub.StoreAcceptanceChecks
```

These checks exercise store-level authentication and data operations. They do not replace browser/HTTP testing of redirects, JSP rendering, filter behavior, or Tomcat session expiration. See [TESTING.md](./TESTING.md) for the executed results and unverified scenarios.

## Documentation and deliverables

- [PROJECT_REPORT.md](./PROJECT_REPORT.md) — assessment report, architecture, implementation notes, deployment, and evidence placeholders.
- [TESTING.md](./TESTING.md) — acceptance-test matrix, actual results, and verification limits.

## In-memory storage warning

This project intentionally has no persistence layer. All data exists only in the running web application's memory. A server restart, redeployment, or application reload restores the sample data and discards registrations and changes made during the session.
