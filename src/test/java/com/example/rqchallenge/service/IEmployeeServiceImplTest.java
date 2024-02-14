package com.example.rqchallenge.service;

import com.example.rqchallenge.bean.CreateEmployeeResponse;
import com.example.rqchallenge.bean.DeleteEmployeeResponse;
import com.example.rqchallenge.bean.Employee;
import com.example.rqchallenge.feign.EmployeeFeingClient;
import com.example.rqchallenge.enums.ErrorCode;
import com.example.rqchallenge.enums.Status;
import com.example.rqchallenge.exception.APIException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getEmployee;
import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getEmployeeInput;
import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getEmployeesResponse;
import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getEmptyEmployeeResponse;
import static com.example.rqchallenge.dataprovider.EmployeeDataProvider.getInvalidEmployeeInput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class IEmployeeServiceImplTest {

    @InjectMocks
    private IEmployeeServiceImpl iEmployeeService;

    @Mock
    private EmployeeFeingClient employeeFeingClient;

    private final Request request = Request.create(Request.HttpMethod.GET, "url",
            new HashMap<>(), null, new RequestTemplate());

    @Test()
    void testGetAllEmployees() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        List<Employee> allEmployees = iEmployeeService.getAllEmployees();
        assertFalse(CollectionUtils.isEmpty(allEmployees));
        assertEquals(2, allEmployees.size());
    }

    @Test
    void testGetAllEmployeesWhenNoRecordsFound() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmptyEmployeeResponse());
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getAllEmployees());
        assertEquals(ErrorCode.NO_RECORDS_FOUND, apiException.getErrorCode());
    }

    @Test()
    void testGetAllEmployeesWhenFeignClientThrowsException() {
        when(employeeFeingClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getAllEmployees());
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        List<Employee> employeesByNameSearch = iEmployeeService.getEmployeesByNameSearch("John");
        assertFalse(CollectionUtils.isEmpty(employeesByNameSearch));
        assertEquals(1, employeesByNameSearch.size());
        assertEquals("John", employeesByNameSearch.get(0).getName());
    }

    @Test
    void testGetEmployeesByNameSearchWhenNoRecordsFound() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getEmployeesByNameSearch("John1"));
        assertEquals(ErrorCode.NO_RECORDS_FOUND, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeesByNameSearchWhenFeignClientThrowsException() {
        when(employeeFeingClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getEmployeesByNameSearch("John"));
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeFeingClient.getEmployeeById("1")).thenReturn(getEmployee());
        Employee employee = iEmployeeService.getEmployeeById("1");
        assertEquals("John", employee.getName());
    }

    @Test
    void testGetEmployeeByIdWhenNoRecordsFound() {
        when(employeeFeingClient.getEmployeeById("1")).thenReturn(null);
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getEmployeeById("1"));
        assertEquals(ErrorCode.NO_RECORDS_FOUND, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeeByIdWhenFeignClientThrowsException() {
        when(employeeFeingClient.getEmployeeById("1"))
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getEmployeeById("1"));
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        Integer highestSalaryOfEmployees = iEmployeeService.getHighestSalaryOfEmployees();
        assertEquals(1000, highestSalaryOfEmployees);
    }

    @Test
    void testGetHighestSalaryOfEmployeesWhenNoRecordsFound() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmptyEmployeeResponse());
        Integer highestSalaryOfEmployees = iEmployeeService.getHighestSalaryOfEmployees();
        assertEquals(0, highestSalaryOfEmployees);
    }

    @Test
    void testGetHighestSalaryOfEmployeesWhenFeignClientThrowsException() {
        when(employeeFeingClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getHighestSalaryOfEmployees());
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        List<String> topTenHighestEarningEmployeeNames = iEmployeeService.getTopTenHighestEarningEmployeeNames();
        assertFalse(CollectionUtils.isEmpty(topTenHighestEarningEmployeeNames));
        assertEquals(2, topTenHighestEarningEmployeeNames.size());
        assertEquals("John", topTenHighestEarningEmployeeNames.get(0));
        assertEquals("Matt", topTenHighestEarningEmployeeNames.get(1));
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNamesWhenNoRecordsFound() {
        when(employeeFeingClient.getAllEmployees()).thenReturn(getEmptyEmployeeResponse());
        List<String> topTenHighestEarningEmployeeNames = iEmployeeService.getTopTenHighestEarningEmployeeNames();
        assertTrue(CollectionUtils.isEmpty(topTenHighestEarningEmployeeNames));
        assertEquals(0, topTenHighestEarningEmployeeNames.size());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNamesWhenFeignClientThrowsException() {
        when(employeeFeingClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getTopTenHighestEarningEmployeeNames());
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testCreateEmployee() {
        when(employeeFeingClient.createEmployee(any())).thenReturn(new CreateEmployeeResponse());
        String status = iEmployeeService.createEmployee(getEmployeeInput());
        assertEquals(Status.SUCCESS.name(), status);
    }

    @Test
    void testCreateEmployeeWithInvalidInput() {
        when(employeeFeingClient.createEmployee(any())).thenReturn(new CreateEmployeeResponse());
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.createEmployee(getInvalidEmployeeInput()));
        assertEquals(ErrorCode.INVALID_INPUT, apiException.getErrorCode());
    }

    @Test
    void testCreateEmployeeWhenFeignClientThrowsException() {
        when(employeeFeingClient.createEmployee(any()))
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        String status = iEmployeeService.createEmployee(getEmployeeInput());
        assertEquals(Status.FAILURE.name(), status);
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeFeingClient.deleteEmployeeById("1")).thenReturn(new DeleteEmployeeResponse());
        String status = iEmployeeService.deleteEmployeeById("1");
        assertEquals(Status.SUCCESS.name(), status);
    }

    @Test
    void testDeleteEmployeeByIdWhenFeignClientThrowsException() {
        when(employeeFeingClient.deleteEmployeeById("1"))
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.deleteEmployeeById("1"));
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }
}