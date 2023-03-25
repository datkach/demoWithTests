package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Photo;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.EmployeesMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class Controller {

    private final EmployeeService employeeService;
    private final EmployeesMapper employeesMapper;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {

        var employee = employeesMapper.INSTANCE.fromDto(requestForSave);
        var dto = employeesMapper.toDto(employeeService.create(employee));

        return dto;
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeesMapper.INSTANCE.toListReadDto(employeeService.getAll());    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging).map(employee -> employeesMapper.INSTANCE.toReadDto(employee));
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK. pam pam param."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found."),
            @ApiResponse(responseCode = "409", description = "Employee already exists")})
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.debug("getEmployeeById() Controller - start: id = {}", id);
        var employee = employeeService.getById(id);
        log.debug("getById() Controller - to dto start: id = {}", id);
        var dto = employeesMapper.toReadDto(employee);
        log.debug("getEmployeeById() Controller - end: name = {}", dto.name);
        return dto;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
        return employeesMapper.INSTANCE.toDto(employeeService.updateById(id, employeesMapper.INSTANCE.fromDto(employeeDto)));
    }


    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeDto> findByCountry(@RequestParam(required = false) String country,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        @RequestParam(defaultValue = "") List<String> sortList,
                                        @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        //Pageable paging = PageRequest.of(page, size);
        //Pageable paging = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString())
                .map(employee -> employeesMapper.INSTANCE.toDto(employee));
    }

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    @GetMapping("/users/countryBy")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getByCountry(@RequestParam(required = true) String country){
       return employeeService.filterByCountry(country).stream()
                .map(employee -> employeesMapper.INSTANCE.toDto(employee))
                .collect(Collectors.toList());
    }
    @GetMapping("/users/countryByAddresses")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getByAddressesCity(@RequestParam( required = true) String city){
        return employeeService.filterByAddressesCity(city)
                .stream()
                .map(employee -> employeesMapper.INSTANCE.toDto(employee))
                .collect(Collectors.toList());
    }
    @GetMapping("/users/activeAddressByCountry")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getActiveAddressByCountry(@RequestParam(required = true) String country){
        return employeeService.filterByActiveAndByCountry(country)
                .stream()
                .map(employee -> employeesMapper.INSTANCE.toDto(employee))
                .collect(Collectors.toList());
    }
    @GetMapping("/users/privateNull")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getByPrivateIsNull(){
        return employeeService.getPrivateIsNullAndChange()
                .stream()
                .map(employee -> employeesMapper.INSTANCE.toDto(employee))
                .collect(Collectors.toList());
    }
    //Сохранение в БД 1000 сущностей Employee
    @PatchMapping("/users/thousand")
    @ResponseStatus(HttpStatus.OK)
    public void createThousandEmployee(){
        employeeService.createThousandPeople();
    }
    //Обновление всех методом Patch с замером скорости
    @PatchMapping("/users/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateAllEmployeesPatch(){
employeeService.updateAllEmployee();
    }
    //Обновление всех методом Put с замером скорости

    @PutMapping("/users/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateAllEmployeesPost(){
        employeeService.updateAllEmployee();
    }
    //Получаем список Employee с прошедшим сроком
    @GetMapping("/users/deprecatedPhoto")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getEmployeeWithDeprecatedPhoto(){
        log.info("getEmployeeWithDeprecatedPhoto() start");
        System.err.println(employeeService.findDeprecatedPhoto());
        return employeesMapper.INSTANCE.toListReadDto(employeeService.findDeprecatedPhoto());
    }
    //Обновляем Photo
    @PutMapping("/users/{id}/refreshPhoto")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDto refreshPhoto(@PathVariable("id") Integer id, @RequestBody PhotoDto photoDto) {
        return employeesMapper.INSTANCE.photoToPhotoDto( employeeService.updatePhoto(id, photoDto) );
    }
    //Добавляем Photo
    @PostMapping("users/{id}/addPhoto")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto addPhotoByEmployee(@PathVariable("id") Integer id, @RequestBody PhotoDto photoDto) {
        return employeesMapper.INSTANCE.toDto(employeeService.newEmployeePhoto(id, employeesMapper.INSTANCE.photoDtoToPhoto(photoDto)));
}
    //получаем Employee с deprecatedPhoto и отсылаем Email
@PatchMapping("users/sendEmails")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailByEmployeeWithDeprecatedPhoto(){
        return employeeService.sendEmailByEmployee();
}


}
