package com.example.employeemanagement.exception;

public class EmployeeNotFoundException extends RuntimeException{
    // Constructor with a custom message
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
