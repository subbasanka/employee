package com.demo.employee.service;

import com.demo.employee.model.Employee;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    Employee getEmployeeById(long employeeId);
}