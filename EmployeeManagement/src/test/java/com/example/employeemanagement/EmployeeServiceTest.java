package com.example.employeemanagement;

import com.example.employeemanagement.DTO.EmployeeDTO;
import com.example.employeemanagement.Model.Employee;
import com.example.employeemanagement.Repository.EmployeeRepository;
import com.example.employeemanagement.Service.EmployeeService;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {


    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(UUID.randomUUID(), "Hadeel", "Aldawsari", "Hadeel@example.com", "HR", new BigDecimal("50000.00"));
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        employeeService.createEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testCreateEmployee_InvalidInput() {
        // Create an invalid employee with an invalid email
        Employee invalidEmployee = new Employee(UUID.randomUUID(), "Hadeel", "Aldawsari", "hadeelgmail.com", "HR", new BigDecimal("50000.00"));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            employeeService.createEmployee(invalidEmployee);
        });
        assertEquals("Email or department validation failed", exception.getMessage());
    }

    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepository.findEmployeeById(employee.getId())).thenReturn(employee);

        EmployeeDTO fetchedEmployee = employeeService.getEmployeeById(employee.getId());

        assertEquals(employee.getEmail(), fetchedEmployee.getEmail());
        assertEquals("Hadeel", fetchedEmployee.getFirstName());
        verify(employeeRepository, times(1)).findEmployeeById(employee.getId());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findEmployeeById(employee.getId())).thenReturn(null);

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployeeById(employee.getId());
        });

        assertEquals("Employee not found", exception.getMessage());
    }



    @Test
    void testUpdateEmployee_Success() {
        Employee updatedEmployee = new Employee(employee.getId(), "UpdatedFirst", "UpdatedLast", "Updated@example.com", "Finance", new BigDecimal("60000.00"));
        when(employeeRepository.findEmployeeById(employee.getId())).thenReturn(employee);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(employee.getId(), updatedEmployee);

        assertEquals("UpdatedFirst", result.getFirstName());
        assertEquals("UpdatedLast", result.getLastName());
        assertEquals("Updated@example.com", result.getEmail());
        assertEquals("Finance", result.getDepartment());
        assertEquals(new BigDecimal("60000.00"), result.getSalary());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Create a new employee with updated data
        Employee updatedEmployee = new Employee(employee.getId(), "UpdatedFirst", "UpdatedLast", "Updated@example.com", "Finance", new BigDecimal("60000.00"));
        // Mock (employee not found) & expect an exception to be thrown
        when(employeeRepository.findEmployeeById(employee.getId())).thenReturn(null);

        EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(employee.getId(), updatedEmployee);
        });

        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.findEmployeeById(employee.getId())).thenReturn(employee);
        employeeService.deleteEmployee(employee.getId());

        verify(employeeRepository, times(1)).delete(employee);
    }


    @Test
    void testGetAllEmployees() {
        // Create a list of employees
        List<Employee> employeeList = List.of(employee, new Employee(UUID.randomUUID(), "Ali", "mohammed", "Ali@gmai.com", "Operation", new BigDecimal("55000.00")));
        when(employeeRepository.findAll()).thenReturn(employeeList);

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        // Assert that the list contains the correct number of employees
        //I added the old employee (Hadeel) and the new is (Ali)
        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

}
