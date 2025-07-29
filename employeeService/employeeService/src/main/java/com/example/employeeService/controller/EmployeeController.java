package com.example.employeeService.controller;

import com.example.employeeService.dto.EmployeeRequestDTO;
import com.example.employeeService.dto.EmployeeResponseDTO;
import com.example.employeeService.model.Employee;
import com.example.employeeService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponseDTO> getAll() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeResponseDTO create(@RequestBody EmployeeRequestDTO requestDTO) {
        return employeeService.saveEmployee(requestDTO);
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO get(@PathVariable Long id) {
        return employeeService.getByIdOrThrow(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.deleteById(id);
    }
}

