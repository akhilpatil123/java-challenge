package com.example.rqchallenge.bean;

import lombok.Data;

@Data
public class CreateEmployeeResponse {
    private String status;
    private CreateEmployeeDTO data;
    private String message;
}
