package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.PassportIsTakenException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PassportServiceBean implements PassportService{
    private final PassportRepository passportRepository;
//    @Transactional
//    @PostConstruct
//    public void init() {
//
//    }
    @Override
    public Passport create(Passport passport) {
        log.info("create() - start");
        passport.setSerialNumber(String.valueOf(UUID.randomUUID()));
        log.info("create() - end: passport - {}",passport.getSerialNumber());
        return passportRepository.save(passport);
    }

    @Override
    public void removeById(Integer id) {
        log.info("removeById() - start");
        passportRepository.deleteById(id);
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
               .filter(s ->s.getEmployee()==null)
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
        List<Passport> passports = getFreePassport();
        if (passports.isEmpty())
    return create(new Passport());
    return passports.stream().findFirst().get();
    }
}
