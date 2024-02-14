package com.example.rqchallenge.employees;

import com.example.rqchallenge.bean.Employee;
import com.example.rqchallenge.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getEmployeeInput;
import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getEmployees;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class IEmployeeControllerImplTest {

    @Mock
    private IEmployeeService iEmployeeService;

    @InjectMocks
    private IEmployeeControllerImpl iEmployeeControllerImpl;

    @Test
    void testGetAllEmployees() {
        when(iEmployeeService.getAllEmployees()).thenReturn(getEmployees());
        ResponseEntity<List<Employee>> allEmployees = iEmployeeControllerImpl.getAllEmployees();
        assertEquals(HttpStatus.OK, allEmployees.getStatusCode());
        assertEquals(2, allEmployees.getBody().size());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        when(iEmployeeService.getEmployeesByNameSearch("John")).thenReturn(getEmployees());
        ResponseEntity<List<Employee>> employeesByNameSearch = iEmployeeControllerImpl.getEmployeesByNameSearch("John");
        assertEquals(HttpStatus.OK, employeesByNameSearch.getStatusCode());
        assertEquals(2, employeesByNameSearch.getBody().size());
    }

    @Test
    void testGetEmployeeById() {
        when(iEmployeeService.getEmployeeById("1")).thenReturn(getEmployees().get(0));
        ResponseEntity<Employee> employeeById = iEmployeeControllerImpl.getEmployeeById("1");
        assertEquals(HttpStatus.OK, employeeById.getStatusCode());
        assertEquals("John", employeeById.getBody().getName());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(iEmployeeService.getHighestSalaryOfEmployees()).thenReturn(1000);
        ResponseEntity<Integer> highestSalaryOfEmployees = iEmployeeControllerImpl.getHighestSalaryOfEmployees();
        assertEquals(HttpStatus.OK, highestSalaryOfEmployees.getStatusCode());
        assertEquals(1000, highestSalaryOfEmployees.getBody());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(iEmployeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(List.of("John", "Matt"));
        ResponseEntity<List<String>> topTenHighestEarningEmployeeNames = iEmployeeControllerImpl.getTopTenHighestEarningEmployeeNames();
        assertEquals(HttpStatus.OK, topTenHighestEarningEmployeeNames.getStatusCode());
        assertEquals(2, topTenHighestEarningEmployeeNames.getBody().size());
    }

    @Test
    void testCreateEmployee() {
        when(iEmployeeService.createEmployee(getEmployeeInput())).thenReturn("Success");
        ResponseEntity<String> createEmployee = iEmployeeControllerImpl.createEmployee(getEmployeeInput());
        assertEquals(HttpStatus.CREATED, createEmployee.getStatusCode());
        assertEquals("Success", createEmployee.getBody());
    }

    @Test
    void testDeleteEmployeeById() {
        when(iEmployeeService.deleteEmployeeById("1")).thenReturn("John");
        ResponseEntity<String> deleteEmployeeById = iEmployeeControllerImpl.deleteEmployeeById("1");
        assertEquals(HttpStatus.OK, deleteEmployeeById.getStatusCode());
        assertEquals("John", deleteEmployeeById.getBody());
    }
}