package com.example.rqchallenge.service;

import com.example.rqchallenge.bean.Employee;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByNameSearch(String searchString);
    Employee getEmployeeById(String id);
    Integer getHighestSalaryOfEmployees();
    List<String> getTopTenHighestEarningEmployeeNames();
    String createEmployee(Map<String, Object> employeeInput);
    String deleteEmployeeById(String id);
}
