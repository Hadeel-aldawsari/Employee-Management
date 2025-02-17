# Employee Management API
## Acknowledgements

Thank you for the opportunity 😊. Everything I have done was based on my experience and journey in learning Spring Boot over the past three months. I hope I have been successful in this endeavor.


## Overview

This project implements an Employee Management API using **Spring Boot**. It includes several key features such as employee creation, retrieval, update, deletion, and listing. 

The application integrates with third-party services for email validation and department verification, handles asynchronous processing, sends email notifications

and provides audit logging to track employee creation.

Additionally, **Controller Advice** is used for global exception handling, **Custom Exceptions** are implemented for specific error scenarios, 

and **DTOs** are used for cleaner data transfer.

## Technologies Used

- **Spring Boot** for building the API.
- **Spring Data JPA** for database integration.
- **H2 Database** for in-memory database storage.
- **JavaMailSender** for email notifications.
- **JUnit** and **Mockito** for unit and integration testing.
- **Spring Async** for asynchronous processing.
- **DTOs** for better data transfer and encapsulation.
- **ControllerAdvice** for global exception handling.

## Features

### 1. API Endpoints

- **POST /api/employees**: Create a new employee by submitting employee details. The email and department are validated through third-party services. The employee is then saved to the database.
- **GET /api/employees/{id}**: Retrieve the details of an employee by their unique ID.
- **PUT /api/employees/{id}**: Update an existing employee’s details.
- **DELETE /api/employees/{id}**: Delete an employee by their unique ID.
- **GET /api/employees**: List all employees in the system.

### 2. Data Model and DTOs

The **Employee** entity contains the following attributes:

- **id** (UUID or autogenerated)
- **firstName** (String)
- **lastName** (String)
- **email** (String)
- **department** (String)
- **salary** (BigDecimal)

DTO (**EmployeeDTO**) is used for cleaner data transfer and abstraction between the API layer and the service layer.


## 3. Database

The project uses **H2** as an in-memory database. Employee data is stored using **JPA/Hibernate** for database operations. This allows the application to persist employee information, including ID, first name, last name, email, department, and salary, during the runtime of the application. The database is created and managed by Spring Boot with the `spring.jpa.hibernate.ddl-auto` property set to `create-drop` for automatic schema creation and deletion.

---

## 4. Exception Handling

Exception handling is implemented to manage various exceptions that might occur during the application's execution:

- **EmployeeNotFoundException**: This exception is thrown when an employee is not found by their ID. A proper HTTP status code (404) and a detailed error message are returned to the client.
  
- **DatabaseException**: This exception handles errors related to database operations, providing appropriate responses for any database failures.

- **InvalidInputException**: Thrown for validation failures such as incorrect email format or invalid department input. The client will receive an HTTP status code of `400` (Bad Request) along with a message specifying the validation issue.

Each exception is handled gracefully by the global exception handler, which ensures that the client always receives a structured error response with relevant details.

---

## 5. Data Validation

- **Email Validation**: The email field is validated to ensure it follows the correct format. This is done both in the controller and by leveraging a third-party service for validation.

- **Department Validation**: The department field accepts only valid departments, which include: **HR**, **Operations**, and **Finance**. Any department outside this list will result in an error message.
  
- **Field Validation**: Validation is applied to all fields to ensure that none of them are missing. The required fields such as first name, last name, email, department, and salary are checked to ensure they are provided. If any of these fields are missing or invalid, a validation error is thrown, and the client receives a proper error message.

---

## 6. Logging

Logging is implemented at various levels to ensure traceability and easier debugging:

- **INFO Level**: Logs general information about the application flow, such as when an employee is created, updated, or retrieved.
  
- **ERROR Level**: Logs errors that occur in the application, such as validation failures, missing employees, or database issues. This helps in diagnosing problems quickly.

Example of logging during employee creation:
```java
logger.info("Creating employee: {}", employee);
logger.error("Validation failed for employee: {}", employee);
```
## 7. Testing

- **Unit Testing**: Unit tests have been written for each service method to ensure the core functionality works as expected. The tests cover operations such as creating, updating, retrieving, and deleting employees.
  
- **Integration Testing**: Integration tests are written for API endpoints using **MockMvc** to ensure that the API functions properly end-to-end. These tests simulate actual HTTP requests and verify the correct responses.

- **Test Cases**:
  - Basic functionality: Tests for creating, updating, deleting, and retrieving employees.
  - Edge cases: Tests for invalid inputs such as incorrect email formats or missing fields.
  - Error scenarios: Tests for situations like employee not found or database errors.
  
## 8. Third-Party API Integration

- **Email Validation**: The email address of the employee is validated using a mock  to ensure it follows a valid format (contains '@' symbol).
  
- **Department Validation**: The employee's department is validated against a mock . Only valid departments such as **HR**, **Operations**, and **Finance** are accepted. Any department outside of this list will trigger an error.
  
- **Email Notification**: After the successful creation of an employee, an email notification is sent to the employee's email address. This serves as a welcome message to the employeeز

## 9. Asynchronous Processing

- **Asynchronous Email Sending**: The email notification process is handled asynchronously using Spring’s `@Async` annotation. 

- The asynchronous processing is specifically applied to the email sending operation to ensure the employee creation process is not delayed by the email dispatch.

## 10. Audit Logging

- **Audit Logging**: Each step of the employee creation process is logged for traceability and debugging purposes. This includes:
  - Validation checks for the employee's details (email, department).
  - Database operations such as saving the employee data.
  - Email notifications sent to the employee.
  
- The logs are recorded at different levels:
  - **INFO**: Logs general information like employee creation and email sending.
  - **ERROR**: Logs any errors or failures, such as validation issues or database failures.
  
- This audit trail ensures that any issues in the employee creation process can be traced and analyzed efficiently.




