package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;

import java.util.List;

public interface PassportService {
    Passport create(Passport passport);
    void removeById(Integer id);
    Passport updateById(Integer id, Passport plane);
    Passport getById(Integer id);
    List<Passport> getFreePassport();
    void generateHundredPassport();
    Passport getFirstFreePassport();

}
