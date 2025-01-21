package com.example.employeemanagement.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @NotEmpty(message = "first name can’t be null")
    @Size(min=2,message= "first name must be more than 1 characters ")
    @Pattern(regexp ="^[a-zA-Z]*$" ,message="should contain letters only")
    @Column(nullable = false, length = 40)
    private String firstName;


    @NotEmpty(message = "last name can’t be null")
    @Size(min=2,message= "last name must be more than 1 characters ")
    @Pattern(regexp ="^[a-zA-Z]*$" ,message="should contain letters only")
    @Column(nullable = false, length = 40)
    private String lastName;

    @NotEmpty(message = "email can’t be null")
    //@Email(message = "Email not valid") //I replace this with mock validation
    @Column(nullable = false, unique = true)
    private String email;


    //here we can use a pattern , in our case I used the mock validation
    @NotEmpty(message = "Department can’t be null")
    @Size(min = 2, max = 50, message = "Department must be between 2 and 50 characters")
    @Column(nullable = false)
    private String department;


    @NotNull(message = "Salary must not be null")
    @Positive(message = "Salary must be a positive value")
    @Column(nullable = false)
    private BigDecimal salary;



}
