package com.example.employeeService.service;

import com.example.employeeService.dto.EmployeeRequestDTO;
import com.example.employeeService.dto.EmployeeResponseDTO;
import com.example.employeeService.model.Employee;
import com.example.employeeService.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeRequestDTO requestDTO;
    private EmployeeResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John", "john@example.com", "pass", 101L);
        requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("John");
        requestDTO.setEmail("john@example.com");
        requestDTO.setPassword("pass");
        requestDTO.setDepartmentId(101L);
        responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("John");
        responseDTO.setEmail("john@example.com");
        responseDTO.setDepartmentId(101L);
    }

    @Test
    void testSaveEmployee() {
        when(modelMapper.map(requestDTO, Employee.class)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeResponseDTO.class)).thenReturn(responseDTO);

        EmployeeResponseDTO saved = employeeService.saveEmployee(requestDTO);
        assertEquals("John", saved.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetByIdFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeResponseDTO.class)).thenReturn(responseDTO);

        Optional<EmployeeResponseDTO> result = Optional.ofNullable(employeeService.getByIdOrThrow(1L));
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }
}
