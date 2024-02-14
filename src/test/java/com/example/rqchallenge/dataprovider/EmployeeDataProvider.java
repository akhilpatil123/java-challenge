package com.example.rqchallenge.dataprovider;

import com.example.rqchallenge.bean.Employee;
import com.example.rqchallenge.bean.EmployeesResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDataProvider {
    public static Employee getEmployee() {
        return Employee.builder()
                .id(1)
                .name("John")
                .salary(1000)
                .age(25).build();
    }

    public static Employee getEmployee2() {
        return Employee.builder()
                .id(1)
                .name("Matt")
                .salary(500)
                .age(10).build();
    }

    public static List<Employee> getEmployees() {
        return Arrays.asList(getEmployee(), getEmployee2());
    }
    public static EmployeesResponse getEmployeesResponse() {
        return EmployeesResponse.builder()
                .status("success")
                .data(getEmployees())
                .message("Success")
                .build();
    }

    public static Map<String, Object> getEmployeeInput() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John");
        employeeInput.put("salary", 1000);
        employeeInput.put("age", 25);
        return employeeInput;
    }

    public static Map<String, Object> getInvalidEmployeeInput() {
        Map<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John");
        employeeInput.put("salary", "XYZ");
        employeeInput.put("age", "!3");
        return employeeInput;
    }

    public static EmployeesResponse getEmptyEmployeeResponse() {
        return EmployeesResponse.builder()
                .status("success")
                .data(Collections.emptyList())
                .message("Success")
                .build();
    }
}
