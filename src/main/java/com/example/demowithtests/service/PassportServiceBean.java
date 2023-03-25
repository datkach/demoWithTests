package com.example.demowithtests.service;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.repository.PassportRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

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
        return passportRepository.save(passport);

    }

    @Override
    public void removeById(Integer id) {
        passportRepository.deleteById(id);
    }

    @Override
    public Passport updateById(Integer id, Passport plane) {
        return passportRepository.findById(id)
                .map(passport -> {
                    passport.setFirstName(plane.getFirstName());
                    passport.setSecondName(plane.getSecondName());
                    passport.setDateOfBirthday(plane.getDateOfBirthday());
                    passport.setExpireDate(plane.getExpireDate());
                    passport.setSerialNumber(plane.getSerialNumber());
                    return passportRepository.save(passport);
                })
                .orElseThrow(() -> new EntityNotFoundException("Passport not found with id = " + id));
    }

    @Override
    public Passport getById(Integer id) {
        return passportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }
}
