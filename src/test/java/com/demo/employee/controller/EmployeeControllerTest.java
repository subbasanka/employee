package com.demo.employee.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import com.demo.employee.exception.ResourceNotFoundException;

import com.demo.employee.model.Employee;
import com.demo.employee.repository.EmployeeRepository;
import com.demo.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeService employeeService;

    /**
     * Method under test: {@link EmployeeController#createEmployee(Employee)}
     */
    @Test
    void testCreateEmployee() throws Exception {
        when(this.employeeRepository.findAll()).thenReturn(new ArrayList<>());

        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployeeController#getEmployeeById(long)}
     */
    @Test
    void testGetEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        when(this.employeeService.getEmployeeById(anyLong())).thenReturn(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"emailId\":\"42\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getEmployeeById(long)}
     */
    @Test
    void testGetEmployeeById2() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        when(this.employeeService.getEmployeeById(anyLong())).thenReturn(employee);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/employees/{id}", 123L);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"emailId\":\"42\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#getEmployeeById(long)}
     */
    @Test
    void testGetEmployeeById3() throws Exception {
        when(this.employeeService.getEmployeeById(anyLong())).thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#createEmployee(Employee)}
     */
    @Test
    void testCreateEmployee2() throws Exception {
        when(this.employeeRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));

        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#deleteEmployee(long)}
     */
    @Test
    void testDeleteEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);
        doNothing().when(this.employeeRepository).delete((Employee) any());
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/employees/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link EmployeeController#deleteEmployee(long)}
     */
    @Test
    void testDeleteEmployee2() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);
        doThrow(new ResourceNotFoundException("An error occurred")).when(this.employeeRepository).delete((Employee) any());
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/employees/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#deleteEmployee(long)}
     */
    @Test
    void testDeleteEmployee3() throws Exception {
        doNothing().when(this.employeeRepository).delete((Employee) any());
        when(this.employeeRepository.findById((Long) any())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/employees/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#deleteEmployee(long)}
     */
    @Test
    void testDeleteEmployee4() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);
        doNothing().when(this.employeeRepository).delete((Employee) any());
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/employees/{id}", 123L);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees() throws Exception {
        when(this.employeeRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees");
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees2() throws Exception {
        when(this.employeeRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/employees");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees3() throws Exception {
        when(this.employeeRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#updateEmployee(long, Employee)}
     */
    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmailId("42");
        employee1.setFirstName("Jane");
        employee1.setId(123L);
        employee1.setLastName("Doe");
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);

        Employee employee2 = new Employee();
        employee2.setEmailId("42");
        employee2.setFirstName("Jane");
        employee2.setId(123L);
        employee2.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(employee2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employees/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"emailId\":\"42\"}"));
    }

    /**
     * Method under test: {@link EmployeeController#updateEmployee(long, Employee)}
     */
    @Test
    void testUpdateEmployee2() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);
        when(this.employeeRepository.save((Employee) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);

        Employee employee1 = new Employee();
        employee1.setEmailId("42");
        employee1.setFirstName("Jane");
        employee1.setId(123L);
        employee1.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(employee1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employees/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#updateEmployee(long, Employee)}
     */
    @Test
    void testUpdateEmployee3() throws Exception {
        Employee employee = new Employee();
        employee.setEmailId("42");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee);
        when(this.employeeRepository.findById((Long) any())).thenReturn(Optional.empty());

        Employee employee1 = new Employee();
        employee1.setEmailId("42");
        employee1.setFirstName("Jane");
        employee1.setId(123L);
        employee1.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(employee1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employees/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

