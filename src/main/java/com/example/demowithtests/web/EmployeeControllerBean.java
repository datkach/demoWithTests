package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.service.PhotoService;
import com.example.demowithtests.util.config.EmployeesMapper;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeControllerSwagger {

    private final EmployeeService employeeService;
    private final EmployeesMapper employeesMapper;
    private final PhotoService photoService;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {

        var employee = employeesMapper.INSTANCE.fromDto(requestForSave);
        var dto = employeesMapper.toReadDto(employeeService.create(employee));

        return dto;
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeesMapper.INSTANCE.toListReadDto(employeeService.getAll());
    }

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
    public EmployeeReadDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody EmployeeDto employeeDto) {
        return employeesMapper.INSTANCE.toReadDto(employeeService.updateById(id, employeesMapper.INSTANCE.fromDto(employeeDto)));
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
    public List<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return employeesMapper.INSTANCE.toListReadDto(
                employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString()).toList());
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
    public List<EmployeeReadDto> getByCountry(@RequestParam(required = true) String country) {
        return employeeService.filterByCountry(country).stream()
                .map(employee -> employeesMapper.INSTANCE.toReadDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/countryByAddresses")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getByAddressesCity(@RequestParam(required = true) String city) {
        return employeeService.filterByAddressesCity(city)
                .stream()
                .map(employee -> employeesMapper.INSTANCE.toReadDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/activeAddressByCountry")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getActiveAddressByCountry(@RequestParam(required = true) String country) {
        return employeeService.filterByActiveAndByCountry(country)
                .stream()
                .map(employee -> employeesMapper.INSTANCE.toReadDto(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/users/privateNull")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getByPrivateIsNull() {
        return employeeService.getPrivateIsNullAndChange()
                .stream()
                .map(employee -> employeesMapper.INSTANCE.toReadDto(employee))
                .collect(Collectors.toList());
    }

    //Сохранение в БД 1000 сущностей Employee
    @PatchMapping("/users/thousand")
    @ResponseStatus(HttpStatus.OK)
    public void createThousandEmployee() {
        employeeService.createThousandPeople();
    }

    //Обновление всех методом Patch с замером скорости
    @PatchMapping("/users/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateAllEmployeesPatch() {
        employeeService.updateAllEmployee();
    }
    //Обновление всех методом Put с замером скорости

    @PutMapping("/users/updateAll")
    @ResponseStatus(HttpStatus.OK)
    public void updateAllEmployeesPost() {
        employeeService.updateAllEmployee();
    }

    //Получаем список Employee с прошедшим сроком
    @GetMapping("/users/deprecatedPhoto")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getEmployeeWithDeprecatedPhoto() {
        log.info("getEmployeeWithDeprecatedPhoto() start");
        System.err.println(employeeService.findDeprecatedPhoto());
        return employeesMapper.INSTANCE.toListReadDto(employeeService.findDeprecatedPhoto());
    }

    //Обновляем Photo
    @PutMapping("/users/{id}/refreshPhoto")
    @ResponseStatus(HttpStatus.OK)
    public PhotoDto refreshPhoto(@PathVariable("id") Integer id, @RequestBody PhotoDto photoDto) {
        return employeesMapper.INSTANCE.photoToPhotoDto(employeeService.updatePhoto(id, photoDto));
    }

    //Добавляем Photo
    @PostMapping("users/{id}/addPhoto")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto addPhotoByEmployee(@PathVariable("id") Integer id, @RequestBody PhotoDto photoDto) {
        return employeesMapper.INSTANCE.toReadDto(employeeService.newEmployeePhoto(id, employeesMapper.INSTANCE.photoDtoToPhoto(photoDto)));
    }

    //получаем Employee с deprecatedPhoto и отсылаем Email
    @PatchMapping("users/sendEmails")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailByEmployeeWithDeprecatedPhoto() {
        return employeeService.sendEmailByEmployee();
    }



// Upload photo by ID
    @PostMapping("/users/photo/{photoId}")
    @ResponseStatus(HttpStatus.OK)
    public void uploadPhoto(@RequestPart(name = "image") MultipartFile multipartFile, @PathVariable Integer photoId) throws IOException {
        photoService.addPhoto(multipartFile, photoId);
    }

    // Get Photo By id
    @GetMapping(value = "/users/photo/{photoId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getPhotoImage(@PathVariable Integer photoId) {
        return photoService.getPhoto(photoId);
    }
    //Добавляем определенно выбранный Passport к Employee
    @PatchMapping("users/{uid}/passports/{pid}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto addPassport(@PathVariable("uid") Integer employeeId,
                                       @PathVariable("pid") Integer passportId) {
        return EmployeesMapper.INSTANCE.toReadDto(employeeService
                .addPassportToEmployee(passportId, employeeId));
    }
    //Добавляем свободный Passport к Employee

    @PatchMapping("users/{uid}/passports")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto addNewPassport(@PathVariable("uid") Integer employeeId) {
        return EmployeesMapper.INSTANCE.toReadDto(employeeService
                .addFreePassportToEmployee(employeeId));
    }
}
