package com.example.demowithtests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DisplayName("Employee Repository Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void saveEmployee() {
        var employee = Employee.builder()
                .name("Dmytro")
                .country("Ukraine")
                .build();
        employeeRepository.save(employee);
        assertEquals("Dmytro", employee.getName());
    }

    @Test
    public void testFindByName() {
        Employee employee1 = Employee.builder().name("John").country("USA").build();
        Employee employee2 = Employee.builder().name("Dmytro").country("Ukraine").build();
        employeeRepository.saveAll(Arrays.asList(employee1, employee2));

        Employee foundEmployee = employeeRepository.findByName("Dmytro");

        assertNotNull(foundEmployee);
        assertEquals("Dmytro", foundEmployee.getName());
        assertEquals("Ukraine", foundEmployee.getCountry());
    }

    @Test
    public void testFindAll() {
        Employee employee1 = Employee.builder()
                .name("John")
                .country("USA")
                .email("john@example.com")
                .build();
        employeeRepository.save(employee1);

        Employee employee2 = Employee.builder()
                .name("Dmytro")
                .country("Ukraine")
                .email("dmytro@example.com")
                .build();
        employeeRepository.save(employee2);

        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(0, 10));

        assertNotNull(employees);
        assertEquals(2, employees.getTotalElements());
        assertEquals("John", employees.getContent().get(0).getName());
        assertEquals("USA", employees.getContent().get(0).getCountry());
        assertEquals("john@example.com", employees.getContent().get(0).getEmail());
        assertEquals("Dmytro", employees.getContent().get(1).getName());
        assertEquals("Ukraine", employees.getContent().get(1).getCountry());
        assertEquals("dmytro@example.com", employees.getContent().get(1).getEmail());
    }

    @Test
    public void testFindEmployeeByAddressesCity() {
        // Создаем тестовых сотрудников с адресами
        Address address1 = Address.builder()
                .city("New York")
                .addressHasActive(true)
                .build();
        Employee employee1 = Employee.builder()
                .name("John")
                .country("USA")
                .addresses(new HashSet<>(Set.of(address1))
                )
                .build();
        employeeRepository.save(employee1);

        Employee employee2 = Employee.builder()
                .name("Peter")
                .country("USA")
                .addresses(new HashSet<>(Set.of(
                        Address.builder()
                                .city("Chicago")
                                .addressHasActive(true)
                .build())))
                .build();
        employeeRepository.save(employee2);

        Employee employee3 = Employee.builder()
                .name("Dmytro")
                .country("Ukraine")
                .addresses(new HashSet<>(Set.of(
                        Address.builder()
                                .city("Odessa")
                                .addressHasActive(true)
                                .build())))
                .build();
        employeeRepository.save(employee3);

        // Проверяем, что метод находит только сотрудников с заданным городом в адресах
        List<Employee> employeesInNewYork = employeeRepository.findEmployeeByAddressesCity("New York");
        assertThat(employeesInNewYork).hasSize(1);
        assertEquals("John", employeesInNewYork.get(0).getName());

        List<Employee> employeesInOdessa = employeeRepository.findEmployeeByAddressesCity("Odessa");
        assertThat(employeesInOdessa).hasSize(1);
        assertEquals("Dmytro", employeesInOdessa.get(0).getName());

        List<Employee> employeesInLondon = employeeRepository.findEmployeeByAddressesCity("Kiev");
        assertThat(employeesInLondon).isEmpty();
    }
}