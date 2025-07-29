package com.example.Department_Service.controller;

import com.example.Department_Service.dto.DepartmentRequestDTO;
import com.example.Department_Service.dto.DepartmentResponseDTO;
import com.example.Department_Service.service.DepartmentService;
import com.example.Department_Service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllDepartments() throws Exception {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(1L);
        dto.setName("Finance");
        dto.setCode("FIN-01");

        when(departmentService.getAllDepartments()).thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Finance"));
    }

    @Test
    void testCreateDepartment() throws Exception {
        DepartmentRequestDTO request = new DepartmentRequestDTO();
        request.setName("IT");
        request.setCode("IT-01");

        DepartmentResponseDTO response = new DepartmentResponseDTO();
        response.setId(2L);
        response.setName("IT");
        response.setCode("IT-01");

        when(departmentService.saveDepartment(request)).thenReturn(response);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("IT"));
    }

    @Test
    void testGetDepartmentById_NotFound() throws Exception {
        when(departmentService.getById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/departments/99"))
                .andExpect(status().isNotFound());
    }
}

