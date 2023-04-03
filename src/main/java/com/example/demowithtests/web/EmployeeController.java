package com.example.demowithtests.web;

import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.PhotoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeController {
        EmployeeReadDto  saveEmployee( EmployeeDto requestForSave);

    //Получение списка юзеров
    List<EmployeeReadDto> getAllUsers();

    Page<EmployeeReadDto> getPage( int page, int size);

    //Получения юзера по id
    EmployeeReadDto getEmployeeById(Integer id);

    //Обновление юзера
    EmployeeReadDto  refreshEmployee( Integer id, EmployeeDto employeeDto);


    void removeEmployeeById(Integer id);

    //Удаление всех юзеров
    void removeAllUsers();

    List<EmployeeReadDto> findByCountry(String country,
                                        int page,
                                        int size,
                                        List<String> sortList,
                                        Sort.Direction sortOrder);

    List<String> getAllUsersC();

    List<String> getAllUsersSort();

    Optional<String> getAllUsersSo();

    List<EmployeeReadDto > getByCountry(String country);

    List<EmployeeReadDto > getByAddressesCity(String city);

    List<EmployeeReadDto > getActiveAddressByCountry(String country);

    List<EmployeeReadDto > getByPrivateIsNull();

    //Сохранение в БД 1000 сущностей Employee

    void createThousandEmployee();

    //Обновление всех методом Patch с замером скорости

    void updateAllEmployeesPatch();
    //Обновление всех методом Put с замером скорости


    void updateAllEmployeesPost();

    //Получаем список Employee с прошедшим сроком

    List<EmployeeReadDto> getEmployeeWithDeprecatedPhoto();

    //Обновляем Photo

    PhotoDto refreshPhoto(Integer id,PhotoDto photoDto);

    //Добавляем Photo

    EmployeeReadDto addPhotoByEmployee(Integer id,PhotoDto photoDto);

    //получаем Employee с deprecatedPhoto и отсылаем Email

    Set<String> sendEmailByEmployeeWithDeprecatedPhoto();
    void uploadPhoto(MultipartFile file, Integer photoId) throws IOException;
    EmployeeReadDto addNewPassport(Integer employeeId);
    EmployeeReadDto addPassport( Integer employeeId, Integer passportId);
}