package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeControllerSwagger extends EmployeeController {
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    EmployeeReadDto  saveEmployee(@RequestBody @Valid EmployeeDto requestForSave);

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers();

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size
    );

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id);

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto  refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto);


    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id);

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers();

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder);

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC();

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort();

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo();

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto > getByCountry(@RequestParam(required = true) String country);

    @GetMapping("/users/countryByAddresses")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto > getByAddressesCity(@RequestParam( required = true) String city);

    @GetMapping("/users/activeAddressByCountry")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto > getActiveAddressByCountry(@RequestParam(required = true) String country);

    @GetMapping("/users/privateNull")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto > getByPrivateIsNull();

    //Сохранение в БД 1000 сущностей Employee
    @PatchMapping("/users/thousand")
    @ResponseStatus(HttpStatus.OK)
    public void createThousandEmployee();

    //Обновление всех методом Patch с замером скорости
    @PatchMapping("/users/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateAllEmployeesPatch();
    //Обновление всех методом Put с замером скорости

    @PutMapping("/users/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateAllEmployeesPost();

    //Получаем список Employee с прошедшим сроком
    @GetMapping("/users/deprecatedPhoto")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getEmployeeWithDeprecatedPhoto();

    //Обновляем Photo
    @PutMapping("/users/{id}/refreshPhoto")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDto refreshPhoto(@PathVariable("id") Integer id, @RequestBody PhotoDto photoDto);

    //Добавляем Photo
    @PostMapping("users/{id}/addPhoto")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto addPhotoByEmployee(@PathVariable("id") Integer id, @RequestBody PhotoDto photoDto);

    //получаем Employee с deprecatedPhoto и отсылаем Email
    @PatchMapping("users/sendEmails")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailByEmployeeWithDeprecatedPhoto();
}
