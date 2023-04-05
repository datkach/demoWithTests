package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.PassportIsTakenException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PassportServiceBean implements PassportService{
    private final PassportRepository passportRepository;
@Override
    public Passport create(Passport passport) {
        log.info("create() - start");
        passport.setSerialNumber(String.valueOf(UUID.randomUUID()));
        passport.setIsFree(Boolean.TRUE);
        log.info("create() - end: passport - {}",passport.getSerialNumber());
        return passportRepository.save(passport);
    }

    @Override
    public void removeById(Integer id) {
        log.info("removeById() - start");
        Passport passport = passportRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
       if(passport.getCurrentState().equals(Passport.PassportState.ACTIVE))
           passport.setCurrentState(Passport.PassportState.CHANGED_FORMAT);
        log.info("removeById() - end");
    }

    @Override
    public Passport updateById(Integer id, Passport plane) {
        log.info("removeById() - start()");
        return passportRepository.findById(id)
                .map(passport -> {
                    passport.setFirstName(plane.getFirstName());
                    passport.setSecondName(plane.getSecondName());
                    passport.setDateOfBirthday(plane.getDateOfBirthday());
                    passport.setExpireDate(plane.getExpireDate());
                    passport.setSerialNumber(plane.getSerialNumber());
                    passport.setCurrentState(Passport.PassportState.ACTIVE);
                    return passportRepository.save(passport);
                })
                .orElseThrow(PassportIsTakenException::new);
    }
    //Получаем Паспорт по ID
    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id).orElseThrow(PassportIsTakenException::new);
    }
//Получаем паспорта без привязки к Employee
    @Override
    public List<Passport> getFreePassport() {
       return passportRepository.findAll()
               .stream()
               .filter(s ->s.getIsFree()==Boolean.TRUE)
               .collect(Collectors.toList());
    }
//Генерация 100 пустых паспортов
    @Override
    public void generateHundredPassport() {
        log.info("generateHundredPassport() - start");
        for (int i = 0; i < 100; i++) {
                create(new Passport());
        }
        log.info("generateHundredPassport() - end");
    }
//Получаем первый свободный паспорт без привязки к Employee.Если такого нет создаётся новый паспорт
    @Override
    public Passport getFirstFreePassport() {
    return getFreePassport().stream().findFirst().orElse(create(new Passport()));
    }
    @Override
    public Passport getFilledPassport(Passport passport, Employee employee){
    log.info("getFilledPassport() - start");
        passport.setFirstName(employee.getName());
        passport.setSecondName(employee.getName());
        passport.setExpireDate(LocalDateTime.now().plusYears(5));
        passport.setIsFree(Boolean.FALSE);
        passport.setCurrentState(Passport.PassportState.ACTIVE);
        passport.setEmployee(employee);
        passportRepository.save(passport);
        log.info("getFilledPassport() - end: passport - {},employee - {}", passport,employee);
        return passport;
    }
}
