package com.example.employeemanagement.Repository;


import com.example.employeemanagement.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
   Employee findEmployeeById(UUID employeeId);
}