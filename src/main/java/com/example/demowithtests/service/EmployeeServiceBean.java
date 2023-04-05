package com.example.demowithtests.service;

import com.example.demowithtests.domain.*;
import com.example.demowithtests.dto.PhotoDto;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.repository.PhotoRepository;
import com.example.demowithtests.util.annotations.ActivateMyAnnotations;
import com.example.demowithtests.util.annotations.Name;
import com.example.demowithtests.util.exception.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PhotoRepository photoRepository;
    private final EmailService emailService;
    private final PassportService passportService;

    @Override
    @ActivateMyAnnotations(Name.class)
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
      /*  var employees = employeeRepository.findAll();
        for(Employee employee : employees){
            if(employee.getIsPrivate())
                employees.remove(employee);
        }*/
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAll(pageable);
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        log.info("getById(Integer id) Service - start: id = {}", id);
        var employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceNotFoundException::new);
        getPrivateMethod(employee);

        if(employee.getIsPrivate()==true){
                throw new ResourcePrivateException();
        }
        log.info("getById(Integer id) Service - end:   = employee {}", employee);
         /*if (employee.getIsDeleted()) {
            throw new EntityNotFoundException("Employee was deleted with id = " + id);
        }*/
        return employee;
    }
    private void getPrivateMethod(Employee employee) {
        log.info("getPrivateById() Service - start: id = {}", employee.getId());
        if(employee.getIsPrivate() == null)
        {
            employee.setIsPrivate(false);
            employeeRepository.save(employee);
        }
        log.info("getPrivateById() Service - end: IsPrivate = {}", employee.getIsPrivate());

    }

    @Override
    @ActivateMyAnnotations(Name.class)
    public Employee updateById(Integer id, Employee employee) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    entity.setName(employee.getName());
                    entity.setEmail(employee.getEmail());
                    entity.setCountry(employee.getCountry());
                    return employeeRepository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceWasDeletedException::new);
        //employee.setIsDeleted(true);
        employeeRepository.delete(employee);
        //repository.save(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }

    /*@Override
    public Page<Employee> findByCountryContaining(String country, Pageable pageable) {
        return employeeRepository.findByCountryContaining(country, pageable);
    }*/

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());
        /*List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());*/

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }

    @Override
    public List<Employee> filterByCountry(String country) {
        return employeeRepository.findByCountry(country);
    }

    @Override
    public List<Employee> filterByAddressesCity(String city) {
        return employeeRepository.findEmployeeByAddressesCity(city);
    }

    @Override
    public List<Employee> filterByActiveAndByCountry(String country) {
        return employeeRepository.findEmployeeAddressesHasActiveAndByCountry(country);
    }

    @Override
    public List<Employee> getPrivateIsNullAndChange() {
        var employees = employeeRepository.queryEmployeeByIsPrivateIsNull();
        for(Employee employee : employees){
            employee.setIsPrivate(Boolean.FALSE);
        }
        employeeRepository.saveAll(employees);
        return employeeRepository.queryEmployeeByIsPrivateIsNull();
    }
//Создание 1к сущностей Employee
    @Override
    public void createThousandPeople() {
        Employee employee;
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            employee = new Employee();
            if(i%2==0){
            employee.setName("Dmytro");
            employee.setGender(Gender.M);
            employee.setCountry("Ukraine");
            employee.setEmail("dmytro" + i + "@gmail.com");
            employee.setIsPrivate(Boolean.FALSE);
        }else{
                employee.setName("Kris");
                employee.setCountry("Spain");
                employee.setEmail("kris" + i + "@gmail.com");
                employee.setGender(Gender.F);
                employee.setIsPrivate(Boolean.FALSE);
            }
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
    }
    //Обновление сущностей Employee
    @Override
    public void updateAllEmployee() {
        Long currentTime = System.currentTimeMillis();
        List<Employee> employees = employeeRepository.findAll();
        for(Employee employee : employees){
            employee.setName(employee.getName() + " update");
        }
        employeeRepository.saveAll(employees);
        Long difference = System.currentTimeMillis() - currentTime;
        log.info("updateAllEmployee() time method - {} ",difference);
    }
//Находим людей с устаревшими фото
    @Override
    public List<Employee> findDeprecatedPhoto() {
        log.info("findDeprecatedPhoto() - start");
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> employeeListDeprecated = new ArrayList<>();
        for (Employee employee : employees) {
            for (Photo photo : employee.getPhotos()) {
                if (photo.getAddDate()
                        .plusYears(5)
                        .plusDays(7)
                        .isBefore(LocalDateTime.now())) {
                    employeeListDeprecated.add(employee);
                }
            }
        }
        return employeeListDeprecated;
    }
// обновляем фото
    @Override
    public Photo updatePhoto(Integer photoId, PhotoDto photoDto) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new PhotoNotFoundException());
        if (photoDto.description != null) photo.setDescription(photoDto.description);
        if (photoDto.cameraType != null) photo.setCameraType(photoDto.cameraType);
        if (photoDto.photoUrl != null) photo.setPhotoUrl(photoDto.photoUrl);
        if (photoDto.addDate != null) photo.setAddDate(LocalDateTime.now());
        return photoRepository.save(photo);
    }
// добавляем новое фото к Employee
    @Override
    public Employee newEmployeePhoto(Integer employeeId, Photo photo) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException());
        employee.getPhotos().add(photo);
        return employeeRepository.save(employee);
    }
    //разсылка на email
    @Override
    public Set<String> sendEmailByEmployee() {
        List<Employee> employee = findDeprecatedPhoto();
        Set<String> mail = new HashSet<>();
        if(employee.size() > 0){
            for(Employee e : employee){
                emailService.sendSimpleMessage(
                        "9482159@stud.op.edu.ua",//e.getEmail();
                        e.getName(),
                       "Please update your photo"
                );
                mail.add(e.getEmail());
            }
        }
        return mail;
    }
//Добавление конкретного паспорта(по id) к Employee
    @Override
    public Employee addPassportToEmployee(Integer passportId, Integer employeeId) {
        log.info("addPassportToEmployee() - start");
        Passport passport =  passportService.getById(passportId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ResourceNotFoundException::new) ;
        if(passport.getIsFree()){
            employee.setPassport(passportService.getFilledPassport(passport,employee));
        employeeRepository.save(employee);
        }
        log.info("addPassportToEmployee() - end: employee - {}",employee);
        return employee;
    }
// Добавляем уже созданный паспорт к Employee
    @Override
    public Employee addFreePassportToEmployee(Integer employeeId) {
        log.info("addFreePassportToEmployee() - start");
        Passport passport = passportService.getFirstFreePassport();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ResourceNotFoundException::new);
        employee.setPassport(passportService.getFilledPassport(passport,employee));
        employeeRepository.save(employee);
        log.info("addFreePassportToEmployee() - end: employee - {}",employee);
        return employee;
    }
// Метод для Оформления данных в добавленном к Employee паспорте
//    private Employee getFilledEmployee(Passport passport, Employee employee) {
//        passport.setFirstName(employee.getName());
//        passport.setSecondName(employee.getName());
//        passport.setExpireDate(LocalDateTime.now().plusYears(5));
//        passport.setIsFree(Boolean.FALSE);
//        passport.setCurrentState(PassportState.ACTIVE);
//        passport.setEmployee(employee);
//        employee.setPassport(passport);
//        employeeRepository.save(employee);
//        return employee;
//    }

    //Паспорт был утерян
    @Override
    public String changeStatusByLost(Integer employeeId){
        log.info("changeStatusByLost() - start");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ResourceNotFoundException::new);
        Passport passport = employee.getPassport();
        if(passport.getCurrentState()
                .equals(Passport.PassportState.ACTIVE))
        {
            passport.setCurrentState(Passport.PassportState.LOST);
            employeeRepository.save(employee);
            return "Successfully changed";
        }
        log.info("changeStatusByLost() - end: employee -{}", employee);

        return "No changes passed";
    }
    //Паспорт был просрочен
    @Override
    public String changeStatusByExpired(Integer employeeId){
        log.info("changeStatusByExpired() - start");
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ResourceNotFoundException::new);
        Passport passport = employee.getPassport();
            if(passport.getExpireDate().isBefore(LocalDateTime.now()))
        {
            passport.setCurrentState(Passport.PassportState.EXPIRED);
            employeeRepository.save(employee);
            return "Successfully changed";
        }
        log.info("changeStatusByExpired() - end: employee -{}", employee);
        return "No changes passed";
    }
    //Получаем новый паспорт с уже существующим
    public Employee getNewPassport(Integer employeeId){
        log.info("getNewPassport() - start");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(ResourceNotFoundException::new);
        Passport passport = employee.getPassport();
        if(passport.getCurrentState().equals(Passport.PassportState.LOST)
                || passport.getCurrentState().equals(Passport.PassportState.EXPIRED))
        {
            passportService.removeById(passport.getId());
             employee =  addFreePassportToEmployee(employeeId);
            employee.getPassport().setPrevious(passport);
            employee.getPassport().setCurrentState(Passport.PassportState.ACTIVE);
            employeeRepository.save(employee);
        }
        log.info("getNewPassport() - end: employee -{}", employee);
        return employee;
    }
}
