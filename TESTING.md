# SkillHub Test Results

**Verification date:** 26 October 2026  
**Build environment:** Windows, JDK 21 compiler with `--release 11`, Maven offline package build  
**Tomcat/browser:** Tomcat 9.0.123 was downloaded, checksum-verified, configured with the WAR, but could not be started by this execution environment.

## Executed checks

The command below was run against the compiled Java classes:

```powershell
java -cp "target\classes;target\test-classes" com.skillhub.StoreAcceptanceChecks
```

All 14 printed assertions completed successfully. These checks call the in-memory `AppDataStore` and verify the predicates described in the table. They are data-store checks, not simulated Servlet requests.

| Check | Executed assertion | Actual result |
|---|---|---|
| S-01 | Demo administrator credentials accepted by authentication service | PASS |
| S-02 | New student record accepted by the in-memory store | PASS |
| S-03 | Demo student credentials accepted by authentication service | PASS |
| S-04 | Demo trainer credentials accepted by authentication service | PASS |
| S-05 | Wrong password rejected by authentication service | PASS |
| S-06 | Duplicate email rejected by student insertion | PASS |
| S-07 | Course creation accepted for a known trainer | PASS |
| S-08 | Course update reflected in the store | PASS |
| S-09 | Student enrollment accepted | PASS |
| S-10 | Duplicate enrollment rejected | PASS |
| S-11 | Enrollment rejected after capacity is reached | PASS |
| S-12 | Attendance record accepted for an enrolled student | PASS |
| S-13 | Student attendance lookup returns the target student's record only | PASS |
| S-14 | Course deletion removes the course and dependent enrollments | PASS |

## Required scenario matrix

`PARTIAL` means the core store rule or credential check was run, but the requested HTTP/UI/filter/session behavior was not exercised. `NOT RUN` means no relevant runtime assertion was performed. There are deliberately no end-to-end `PASS` marks in this table.

| Test case ID | Test scenario | Expected result | Actual result | Pass/Fail |
|---|---|---|---|---|
| TC-01 | Administrator login | Admin session created and dashboard redirect succeeds | Admin credentials passed the data-store authentication check; servlet redirect/session/dashboard not exercised | PARTIAL |
| TC-02 | Student registration | Valid form creates student and confirms success | Store insertion passed; registration servlet validation and response not exercised | PARTIAL |
| TC-03 | Student login | Student session created and student dashboard redirect succeeds | Student credentials passed the data-store authentication check; servlet flow not exercised | PARTIAL |
| TC-04 | Trainer login | Trainer session created and trainer dashboard redirect succeeds | Trainer credentials passed the data-store authentication check; servlet flow not exercised | PARTIAL |
| TC-05 | Incorrect password | Login is rejected with an error | Authentication service rejected the wrong password; login JSP error response not exercised | PARTIAL |
| TC-06 | Empty form submission | Server rejects missing required fields | Not run through the registration/login servlet | NOT RUN |
| TC-07 | Duplicate student registration | Duplicate email/username rejected with an error | Duplicate email was rejected by the store; registration servlet's HTTP response not exercised | PARTIAL |
| TC-08 | Course creation | Admin form creates a visible course | Store-level course creation passed; admin form, role filter, and JSP not exercised | PARTIAL |
| TC-09 | Course updating | Admin changes persist and appear in course list | Store-level update passed; servlet form submission and rendered table not exercised | PARTIAL |
| TC-10 | Course deletion | Admin deletion removes the course and dependent records | Store-level deletion and dependent-enrollment cleanup passed; HTTP action not exercised | PARTIAL |
| TC-11 | Student course enrolment | Student enrollment request is stored as Pending | Store-level enrollment passed; servlet/session redirect not exercised | PARTIAL |
| TC-12 | Duplicate enrolment prevention | Repeated student/course enrollment is refused | Store-level duplicate check passed; user-facing servlet error not exercised | PARTIAL |
| TC-13 | Course capacity restriction | No enrollment is accepted after capacity is reached | Store-level capacity check passed; servlet response not exercised | PARTIAL |
| TC-14 | Attendance recording | Assigned trainer can record valid attendance for an enrolled student | Store-level attendance insertion passed; trainer session/course assignment route not exercised | PARTIAL |
| TC-15 | Student views personal attendance | Student sees only their own records | Student-scoped store query passed; authenticated JSP rendering/authorization not exercised | PARTIAL |
| TC-16 | Unauthorized admin page access | Anonymous/student/trainer access is denied | Not run against a Servlet container | NOT RUN |
| TC-17 | Session timeout | Expired session is treated as unauthenticated | Not run against a Servlet container | NOT RUN |
| TC-18 | Logout and session invalidation | Logout invalidates the session and protected pages redirect | Not run against a Servlet container | NOT RUN |

## Build and deployment evidence

- `mvn -o package` completed successfully and created `target/skillhub-training-academy.war`.
- Maven compiled the main sources and the dependency-free acceptance-check class with `--release 11`.
- The WAR was copied into Tomcat 9.0.123's `webapps` directory, but Tomcat could not be started; runtime deployment is not confirmed.
- Port 8080 belongs to `TNSLSNR.EXE` / Oracle XML DB and returns HTTP 401 for all paths, including `/index.jsp`. This is not a SkillHub response. Tomcat was configured on port 18080.
- The execution environment denied the process-launch request, so port 18080 was not reachable for browser testing.
- Maven Central could not be resolved by DNS during an attempt to obtain embedded-container dependencies. Consequently, no embedded Tomcat or browser automation was added or claimed.

## How to complete end-to-end verification

After starting the prepared Tomcat 9 instance (or installing one locally):

1. Build with `mvn clean package`.
2. Open `http://localhost:18080/skillhub-training-academy/` on this machine.
3. Run the required scenarios in the matrix in a browser, including expired-session behavior.
4. Record observed status/message and replace `PARTIAL`/`NOT RUN` only after the corresponding route has been exercised and verified.
5. Add genuine screenshots or server logs as evidence. Do not reuse placeholders as evidence.
