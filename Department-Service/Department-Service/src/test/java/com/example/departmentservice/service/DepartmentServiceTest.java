package com.example.departmentservice.service;

import com.example.departmentservice.dto.DepartmentRequestDTO;
import com.example.departmentservice.dto.DepartmentResponseDTO;
import com.example.departmentservice.model.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private DepartmentRequestDTO requestDTO;
    private DepartmentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        department = new Department(1L, "Admin", "ADM-01");
        requestDTO = new DepartmentRequestDTO();
        requestDTO.setName("Admin");
        requestDTO.setCode("ADM-01");

        responseDTO = new DepartmentResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Admin");
        responseDTO.setCode("ADM-01");
    }

    @Test
    void testSaveDepartment() {
        when(modelMapper.map(requestDTO, Department.class)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(modelMapper.map(department, DepartmentResponseDTO.class)).thenReturn(responseDTO);

        DepartmentResponseDTO saved = departmentService.saveDepartment(requestDTO);
        assertEquals("Admin", saved.getName());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testGetDepartmentById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(modelMapper.map(department, DepartmentResponseDTO.class)).thenReturn(responseDTO);

        DepartmentResponseDTO found = departmentService.getById(1L).orElse(null);
        assertNotNull(found);
        assertEquals("Admin", found.getName());
    }
}
