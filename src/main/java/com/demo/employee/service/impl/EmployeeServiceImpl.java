package com.demo.employee.service.impl;

import lombok.AllArgsConstructor;
import com.demo.employee.model.Employee;
import com.demo.employee.repository.EmployeeRepository;
import com.demo.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }
}