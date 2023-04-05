package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.PhotoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {

    Employee create(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    //Page<Employee> findByCountryContaining(String country, Pageable pageable);
    /**
     * @param country  Filter for the country if required
     * @param page            number of the page returned
     * @param size            number of entries in each page
     * @param sortList        list of columns to sort on
     * @param sortOrder       sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
    List<String> getAllEmployeeCountry();

    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
    List<String> getSortCountry();

    Optional<String> findEmails();
     List<Employee> filterByCountry(String country);
     List<Employee> filterByAddressesCity(String city);
    List<Employee> filterByActiveAndByCountry(String country);
    List<Employee> getPrivateIsNullAndChange();
    void createThousandPeople();
    void updateAllEmployee();
    List<Employee> findDeprecatedPhoto();

    Photo updatePhoto(Integer photoId, PhotoDto photoDto);

    Employee newEmployeePhoto(Integer employeeId, Photo photo);
    Set<String > sendEmailByEmployee();
    Employee addPassportToEmployee(Integer passportId, Integer employeeId);
Employee addFreePassportToEmployee(Integer employeeId);
    String changeStatusByLost(Integer employeeId);

    String changeStatusByExpired(Integer employeeId);

    Employee getNewPassport(Integer employeeId);
}
