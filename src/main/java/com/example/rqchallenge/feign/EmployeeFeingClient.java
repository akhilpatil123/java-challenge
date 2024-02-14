package com.example.rqchallenge.feign;

import com.example.rqchallenge.bean.CreateEmployeeDTO;
import com.example.rqchallenge.bean.CreateEmployeeResponse;
import com.example.rqchallenge.bean.DeleteEmployeeResponse;
import com.example.rqchallenge.bean.Employee;
import com.example.rqchallenge.bean.EmployeesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient( name = "dummyRestAPIExampleClient", url = "http://dummy.restapiexample.com/api/v1")
public interface EmployeeFeingClient {

    @GetMapping(value = "/employees")
    EmployeesResponse getAllEmployees();

    @GetMapping(value = "/employee/{id}")
    Employee getEmployeeById(String id);

    @PostMapping(value = "/create")
    CreateEmployeeResponse createEmployee(CreateEmployeeDTO employee);

    @DeleteMapping(value = "/delete/{id}")
    DeleteEmployeeResponse deleteEmployeeById(String id);
}
