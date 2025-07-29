package com.example.employeeService.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Long departmentId;
}
