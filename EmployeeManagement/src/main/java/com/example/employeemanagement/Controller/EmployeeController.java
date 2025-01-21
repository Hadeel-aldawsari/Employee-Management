package com.example.employeemanagement.Controller;

import com.example.employeemanagement.ApiResponse.ApiResponse;
import com.example.employeemanagement.DTO.EmployeeDTO;
import com.example.employeemanagement.Model.Employee;
import com.example.employeemanagement.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    // Create Employee
    @PostMapping("/create")
    public ResponseEntity createEmployee(@Valid @RequestBody Employee employee) {
        logger.info("Creating new employee with email: {}", employee.getEmail());

        //if there is any issue the service will throw ex
        employeeService.createEmployee(employee);

        logger.info("Employee created successfully with email: {}", employee.getEmail());
        return ResponseEntity.status(200).body(new ApiResponse("Employee Created Successfully"));
    }

    // Get Employee by ID
    @GetMapping("/get-employee/by-id/{id}")
    public ResponseEntity getEmployeeById(@PathVariable UUID id) {
        logger.info("Fetching employee with ID: {}", id);

        EmployeeDTO employee = employeeService.getEmployeeById(id); // Throws EmployeeNotFoundException if not found

        logger.info("Employee found with ID: {}", id);
        return ResponseEntity.status(200).body(employee);
    }

    // Update Employee
    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable UUID id, @Valid @RequestBody Employee updatedEmployee) {
        logger.info("Updating employee with ID: {}", id);

        Employee updated = employeeService.updateEmployee(id, updatedEmployee);  // Throws EmployeeNotFoundException if not found

        logger.info("Employee updated with ID: {}", id);
        return ResponseEntity.status(200).body(updated);
    }

    // Delete Employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable UUID id) {
        logger.info("Deleting employee with ID: {}", id);

        employeeService.deleteEmployee(id);  // Throws EmployeeNotFoundException if not found

        logger.info("Employee with ID: {} deleted successfully", id);
        return ResponseEntity.status(200).body(new ApiResponse("Employee With ID:" + id + " Deleted Successfully"));
    }

    // List All Employees
    @GetMapping("/get-all-employee")
    public ResponseEntity getAllEmployees() {
        logger.info("Fetching all employees");

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        logger.info("Retrieved {} employees", employees.size());
        return ResponseEntity.status(200).body(employees);
    }
}
