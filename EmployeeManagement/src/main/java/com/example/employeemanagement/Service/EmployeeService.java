package com.example.employeemanagement.Service;

import com.example.employeemanagement.DTO.EmployeeDTO;
import com.example.employeemanagement.Model.Employee;
import com.example.employeemanagement.Repository.EmployeeRepository;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public void createEmployee(@Valid Employee employee) {
        logger.info("Starting the creation process for employee: {}", employee.getEmail());

        // Validate email
        if (!validateEmailWithThirdPartyAPI(employee.getEmail())) {
            logger.error("Email validation failed for employee: {}", employee.getEmail());
            throw new InvalidInputException("Email validation failed");
        }
        logger.info("Email validation passed for employee: {}", employee.getEmail());

        // Validate department
        if (!validateDepartmentWithThirdPartyAPI(employee.getDepartment())) {
            logger.error("Department validation failed for employee: {}", employee.getEmail());
            throw new InvalidInputException("Department validation failed");
        }
        logger.info("Department validation passed for employee: {}", employee.getEmail());

        // Saving the employee to the database
        logger.info("Saving employee with ID: {}", employee.getId());
        employeeRepository.save(employee);

        // success
        logger.info("Employee created successfully with ID: {}", employee.getId());

        // Sending email after employee creation
        try {
            String emailSubject = "Welcome!";
            String emailBody = "Dear " + employee.getFirstName() + ",\n\n" +
                    "Welcome to Our employee management system.\n" +
                    "We are thrilled to have you on board and excited to work with you. " +
                    "You can now log in and start your journey with us.\n\n" +
                    "Best regards,\n" +
                    "HR Team";
            emailService.sendEmail(employee.getEmail(), emailSubject, emailBody);

            // Log email sent successfully
            logger.info("Welcome email sent successfully to: {}", employee.getEmail());
        } catch (Exception e) {
            // Log email sending failure
            logger.error("Failed to send welcome email to: {}", employee.getEmail(), e);
        }
    }


    public EmployeeDTO getEmployeeById(UUID id) {
        logger.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            logger.error("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException("Employee not found");
        }

        logger.info("Employee found with ID: {}", id);
        EmployeeDTO employeeDTO = new EmployeeDTO(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );

        return employeeDTO;
    }

    public Employee updateEmployee(UUID id, Employee updatedEmployee) {
        logger.info("Updating employee with ID: {}", id);
        Employee existingEmployee =employeeRepository.findEmployeeById(id);
        if (existingEmployee == null) {
            logger.error("Employee not found for update with ID: {}", id);
            throw new EmployeeNotFoundException("Employee not found");
        }

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        logger.info("Employee with ID: {} updated successfully", id);
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(UUID id) {
        logger.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            logger.error("Employee not found for deletion with ID: {}", id);
            throw new EmployeeNotFoundException("Employee not found");
        }
        employeeRepository.delete(employee);
        logger.info("Employee with ID: {} deleted successfully", id);
    }

    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Fetching all employees");

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
            employeeDTOList.add(employeeDTO);
        }

        return employeeDTOList;
    }



    private boolean validateEmailWithThirdPartyAPI(String email) {
        logger.info("Validating email: {}", email);
        return email.contains("@");
    }

    private boolean validateDepartmentWithThirdPartyAPI(String department) {
        logger.info("Validating department: {}", department);
        return department != null && (department.equalsIgnoreCase("HR")
                || department.equalsIgnoreCase("Operation")
                || department.equalsIgnoreCase("Finance"));
    }
}
