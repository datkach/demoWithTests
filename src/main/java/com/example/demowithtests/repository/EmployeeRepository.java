package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
//@Component
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);
    @Query(value = "select e from Employee e where e.country =?1")
    List<Employee> findByCountry(String country);

    @Query(value = " SELECT * from users join addresses a on users.id = a.employee_id where a.city=?1", nativeQuery = true)
    List<Employee> findEmployeeByAddressesCity(String city);

    //@Query("select e from Employee e join e.addresses s where s.addressHasActive= true and s.city is not null")
    @Query("select e from Employee e join e.addresses s where s.addressHasActive= true and s.country =?1")
    List<Employee> findEmployeeAddressesHasActiveAndByCountry(String country);
}
