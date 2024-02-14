package com.example.rqchallenge.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmployeesResponse {
    private String status;
    private List<Employee> data;
    private String message;
}
