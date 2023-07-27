# My first big project on Spring :star:

### Stack of technologies :computer:

1. Project: three-layer architecture.
2. Build automation tool: Maven.
3. Frameworks: Hibernate, Spring Boot, Spring Data JPA, Spring Security.
4. Database: MySQL.
5. View: Thymeleaf, HTML, Bootstrap.
6. Database Migrations using Flyway.
7. Tools: Lombok.

________________________________________________________________________________________________

### Required environment :wrench:

1. __Java 11__
2. MySQL 5.7.36
3. (optional) Maven 3.8.6
4. (optional) Tomcat 8
5. (optional) Git
6. (optional) IDE (Eclipse or IntelliJ IDEA)

________________________________________________________________________________________________
### Task :pencil2:

- Develop a Web application that allows users to register and authenticate.
- Unauthenticated users do not have access to user management (they can only access the registration form or authentication form).
- Authenticated users see the "users" table (ID, name, soap, registration date, last login date, status) with users.
- The table in the left column contains checkboxes for multiple selection, in the column header there is a check box "select all/deselect".
- Above the toolbar table with actions: Block, Unblock, Delete.
- The user can delete or block himself — at the same time, he must be logged out immediately.
- If someone else blocks or deletes the user, then at any next action the user is redirected to the login page.
- When registering, it should be possible to use any password, even from a single character.
- The blocked user cannot log in, the deleted user can re-register.
