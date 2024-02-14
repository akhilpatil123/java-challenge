package com.example.rqchallenge.util;

import com.example.rqchallenge.bean.CreateEmployeeDTO;
import com.example.rqchallenge.enums.ErrorCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.example.rqchallenge.constants.LoggingConstants.ACTION;
import static com.example.rqchallenge.constants.LoggingConstants.CLASS;
import static com.example.rqchallenge.constants.LoggingConstants.DEBUG_MESSAGE;
import static com.example.rqchallenge.constants.LoggingConstants.INPUT;
import static com.example.rqchallenge.constants.LoggingConstants.TIME_ELAPSED;

@UtilityClass
@Slf4j
public class EmployeeUtil {
    public static CreateEmployeeDTO getCreateEmployeeDTO(Map<String, Object> employeeInput) {
        try {
            CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO();
            createEmployeeDTO.setName((String) employeeInput.get("name"));
            createEmployeeDTO.setSalary(Integer.parseInt(employeeInput.get("salary").toString()));
            createEmployeeDTO.setAge(Integer.parseInt(employeeInput.get("age").toString()));
            return createEmployeeDTO;
        } catch (Exception e) {
            log.error("Error while parsing employee input", e);
            throw ExceptionUtil.getAPIException(ErrorCode.INVALID_INPUT);
        }
    }

    public static Map<String, Object> getLogMap(String className, String methodName, Object input, String message) {
        return Map.of(CLASS, className, ACTION, methodName, INPUT, input, DEBUG_MESSAGE, message);
    }

    public static Map<String, Object> getLogMap(String className, String methodName, long time) {
        return Map.of(CLASS, className, ACTION, methodName, TIME_ELAPSED, time);
    }
}
