package com.example.rqchallenge.service;

import com.example.rqchallenge.bean.CreateEmployeeDTO;
import com.example.rqchallenge.bean.CreateEmployeeResponse;
import com.example.rqchallenge.bean.DeleteEmployeeResponse;
import com.example.rqchallenge.bean.Employee;
import com.example.rqchallenge.bean.EmployeesResponse;
import com.example.rqchallenge.enums.ErrorCode;
import com.example.rqchallenge.enums.Status;
import com.example.rqchallenge.feign.EmployeeFeingClient;
import com.example.rqchallenge.util.EmployeeUtil;
import com.example.rqchallenge.util.ExceptionUtil;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.rqchallenge.constants.LoggingConstants.LOG_MESSAGE;
import static com.example.rqchallenge.util.EmployeeUtil.getLogMap;

@Service
@Slf4j
public class IEmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeFeingClient employeeFeingClient;

    @Override
    public List<Employee> getAllEmployees() {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getAllEmployees", "NA", "Getting All Employees"));
        try{
            EmployeesResponse response = employeeFeingClient.getAllEmployees();
            List<Employee> employees = response.getData();

            if(CollectionUtils.isEmpty(employees)){
                log.error("IEmployeeServiceImpl : getAllEmployees : No employees found");
                throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
            }

            return response.getData();
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : getAllEmployees : Exception while getting all employees : {}", feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getEmployeesByNameSearch", searchString, "Getting Employees by name search"));
        try{
            EmployeesResponse response = employeeFeingClient.getAllEmployees();
            List<Employee> employees = response.getData().stream()
                    .filter(employee -> employee.getName().toLowerCase().contains(searchString.toLowerCase()))
                    .collect(Collectors.toList());

            if(CollectionUtils.isEmpty(employees)){
                log.error("IEmployeeServiceImpl : getEmployeesByNameSearch : No employees found for search string : {}", searchString);
                throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
            }

            return employees;
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : getEmployeesByNameSearch : Exception while getting employees by name search : {} Exception : {}", searchString, feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public Employee getEmployeeById(String id) {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getEmployeeById", id, "Getting Employee by id"));
        try{
            Employee employee = employeeFeingClient.getEmployeeById(id);

            if(Objects.isNull(employee)){
                log.error("IEmployeeServiceImpl : getEmployeeById : No employee found for id : {}", id);
                throw ExceptionUtil.getAPIException(ErrorCode.NO_RECORDS_FOUND);
            }

            return employee;
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : getEmployeeById : Exception while getting employee by id : {} Exception : {}", id , feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getHighestSalaryOfEmployees", "NA", "Getting highest salary of employees"));
        try{
            EmployeesResponse response = employeeFeingClient.getAllEmployees();
            return response.getData().stream()
                    .mapToInt(Employee::getSalary)
                    .max()
                    .orElse(0);
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : getHighestSalaryOfEmployees : Exception while getting highest salary of employees. Exception : {}", feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "getTopTenHighestEarningEmployeeNames", "NA", "Getting top ten highest earning employee names"));
        try{
            EmployeesResponse response = employeeFeingClient.getAllEmployees();
            return response.getData().stream()
                    .sorted((e1, e2) -> e2.getSalary() - e1.getSalary())
                    .limit(10)
                    .map(Employee::getName)
                    .collect(Collectors.toList());
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : getTopTenHighestEarningEmployeeNames : Exception while getting top ten highest earning employee names. Exception : {}", feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }

    @Override
    public String createEmployee(Map<String, Object> employeeInput) {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "createEmployee", employeeInput, "Creating employee"));
        CreateEmployeeDTO createEmployeeDTO = EmployeeUtil.getCreateEmployeeDTO(employeeInput);
        try {
            CreateEmployeeResponse response = employeeFeingClient.createEmployee(createEmployeeDTO);
            log.info("IEmployeeServiceImpl : createEmployee : Employee created successfully : {}", response);
            return Status.SUCCESS.name();
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : createEmployee : Exception while creating employee : {} Exception : {}", employeeInput, feignException.getMessage());
            return Status.FAILURE.name();
        }
    }

    @Override
    public String deleteEmployeeById(String id) {
        log.info(LOG_MESSAGE, getLogMap(this.getClass().getSimpleName(), "deleteEmployeeById", id, "Deleting employee by id"));
        try{
            DeleteEmployeeResponse response = employeeFeingClient.deleteEmployeeById(id);
            log.info("IEmployeeServiceImpl : deleteEmployeeById : Employee deleted successfully : {}, Response : {}", id, response);
            return Status.SUCCESS.name();
        } catch (FeignException feignException){
            log.error("IEmployeeServiceImpl : deleteEmployeeById : Exception while deleting employee by id : {} Exception : {}", id, feignException.getMessage());
            throw ExceptionUtil.getAPIException(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API);
        }
    }
}
