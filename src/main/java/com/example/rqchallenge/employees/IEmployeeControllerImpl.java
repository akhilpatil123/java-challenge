package com.example.rqchallenge.employees;

import com.example.rqchallenge.bean.Employee;
import com.example.rqchallenge.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.rqchallenge.constants.LoggingConstants.LOG_MESSAGE;
import static com.example.rqchallenge.util.EmployeeUtil.getLogMap;

@Slf4j
@Service
public class IEmployeeControllerImpl implements IEmployeeController{

    @Autowired
    private IEmployeeService iEmployeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees(){
        long beforeCallingService = System.currentTimeMillis();
        List<Employee> employees = iEmployeeService.getAllEmployees();
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getAllEmployees", System.currentTimeMillis() - beforeCallingService));
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        long beforeCallingService = System.currentTimeMillis();
        List<Employee> employees = iEmployeeService.getEmployeesByNameSearch(searchString);
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getEmployeesByNameSearch", System.currentTimeMillis() - beforeCallingService));
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        long beforeCallingService = System.currentTimeMillis();
        Employee employee = iEmployeeService.getEmployeeById(id);
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getEmployeeById", System.currentTimeMillis() - beforeCallingService));
        return ResponseEntity.ok(employee);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        long beforeCallingService = System.currentTimeMillis();
        Integer highestSalary = iEmployeeService.getHighestSalaryOfEmployees();
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getHighestSalaryOfEmployees", System.currentTimeMillis() - beforeCallingService));
        return ResponseEntity.ok(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        long beforeCallingService = System.currentTimeMillis();
        List<String> topTenHighestEarningEmployeeNames = iEmployeeService.getTopTenHighestEarningEmployeeNames();
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getTopTenHighestEarningEmployeeNames", System.currentTimeMillis() - beforeCallingService));
        return ResponseEntity.ok(topTenHighestEarningEmployeeNames);
    }

    @Override
    public ResponseEntity<String> createEmployee(Map<String, Object> employeeInput) {
        long beforeCallingService = System.currentTimeMillis();
        String status = iEmployeeService.createEmployee(employeeInput);
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "createEmployee", System.currentTimeMillis() - beforeCallingService));
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        long beforeCallingService = System.currentTimeMillis();
        String name = iEmployeeService.deleteEmployeeById(id);
        log.debug(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "deleteEmployeeById", System.currentTimeMillis() - beforeCallingService));
        return ResponseEntity.ok(name);
    }
}
