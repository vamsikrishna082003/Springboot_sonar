package com.example.employeeService.dto;

import lombok.Data;

@Data
public class EmployeeRequestDTO {
    private String name;
    private String email;
    private String password;
    private Long departmentId;

}