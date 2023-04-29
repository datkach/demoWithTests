package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.EmployeeServiceBean;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class ServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceBean employeeService;

    private Employee employee;
    @Test
    public void testUnknownId() {

        Employee employee = new Employee();
        employee.setId(89);
        employee.setName("Mark");

        given(employeeRepository.findById(anyInt())).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getById(employee.getId()) );
    }
    @Test
    void testCreate() {
        Employee employee = new Employee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeService.create(employee);

        assertEquals(employee, createdEmployee);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetAll() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.getAll();

        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllWithPagination() {
        Page<Employee> employeePage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

        Page<Employee> employees = employeeService.getAllWithPagination(pageable);

        assertEquals(employeePage, employees);
        verify(employeeRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetById() {
        Integer employeeId = 1;
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getById(employeeId);

        assertEquals(employee, foundEmployee);
        verify(employeeRepository, times(1)).findById(employeeId);
    }
}
