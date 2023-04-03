package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeControllerSwagger extends EmployeeController {
    @Override
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto  saveEmployee(EmployeeDto requestForSave);

    //Получение списка юзеров
    @Override
    @Operation(summary = "This endpoint get all employees.", description = "Show all employees in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getAllUsers();

    @Override
    @Operation(summary = "This endpoint get all employees with pagination.", description = "Show all employees in DB with pagination", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Page<EmployeeReadDto> getPage( int page, int size);
    //Получения юзера по id
    @Override
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto getEmployeeById(Integer id);

    //Обновление юзера
    @Override
    @Operation(summary = "This endpoint replace employee", description = "Refresh employee in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto  refreshEmployee(Integer id, EmployeeDto employeeDto);


    //Удаление по id
    @Override
    @Operation(summary = "This endpoint remove employee by ID", description = "Remove employee in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removeEmployeeById(Integer id);

    //Удаление всех юзеров
    @Override
    @Operation(summary = "This endpoint remove all employees", description = "Remove all employees in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void removeAllUsers();

    @Override
    @Operation(summary = "This endpoint returns employee from specific country with pagination", description = "Find employee based on country in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    @Override
    @Operation(summary = "This endpoint gets all employees with country", description = "Find all employees with country in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<String> getAllUsersC();

    @Override
    @Operation(summary = "This endpoint gets all employees with country Sort", description = "Find all employees with country Sort in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<String> getAllUsersSort();

    @Override
    @Operation(summary = "This endpoint gets all employees with email", description = "Find all employees with email in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Optional<String> getAllUsersSo();

    @Override
    @Operation(summary = "This endpoint gets all employees with entered country", description = "Find all employees with entered country in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto > getByCountry(String country);

    @Override
    @Operation(summary = "This endpoint gets all employees with entered city", description = "Find all employees with entered city in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto > getByAddressesCity(String city);

    @Override
    @Operation(summary = "This endpoint gets all employees with active country", description = "Find all employees with active country in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto > getActiveAddressByCountry(String country);

    @Override
    @Operation(summary = "This endpoint gets all employees without private", description = "Find all employees without private in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto > getByPrivateIsNull();

    //Сохранение в БД 1000 сущностей Employee
    @Override
    @Operation(summary = "This endpoint generate employees", description = "generate 1000 employees", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void createThousandEmployee();

    //Обновление всех методом Patch с замером скорости
    @Override
    @Operation(summary = "This endpoint update employees", description = " Patch update all employees", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void updateAllEmployeesPatch();
    //Обновление всех методом Put с замером скорости

    @Override
    @Operation(summary = "This endpoint update employees", description = "Post update all employees", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    void updateAllEmployeesPost();

    //Получаем список Employee с прошедшим сроком
    @Override
    @Operation(summary = "This endpoint find all employees with deprecated photos", description = "find all with deprecated photo", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    List<EmployeeReadDto> getEmployeeWithDeprecatedPhoto();

    //Обновляем Photo
    @Override
    @Operation(summary = "This endpoint refresh photo", description = "Refresh photo in DB", tags = {"Photo"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    PhotoDto refreshPhoto(Integer id,PhotoDto photoDto);

    //Добавляем Photo
    @Override
    @Operation(summary = "This endpoint add photo to employee", description = "Add photo to employee in DB", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    EmployeeReadDto addPhotoByEmployee(Integer id, PhotoDto photoDto);

    //получаем Employee с deprecatedPhoto и отсылаем Email
    @Override
    @Operation(summary = "This endpoint sends email to all employees with deprecated photos", description = "sends email to those, whose photo is expired", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
    Set<String> sendEmailByEmployeeWithDeprecatedPhoto();
    @Override
    @Operation(summary = "This endpoint add PassPort to  employee by Passport Id and Employee Id", description = "add PassPort to  employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
     EmployeeReadDto addPassport(Integer employeeId, Integer passportId);
    @Override
    @Operation(summary = "This endpoint add first free PassPort to employee by Employee Id", description = "add PassPort to  employee", tags = {"Employee"})
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "OK."))
     EmployeeReadDto addNewPassport(Integer employeeId);
}
