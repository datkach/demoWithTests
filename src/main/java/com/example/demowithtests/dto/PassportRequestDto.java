package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Passport;

import java.time.LocalDateTime;

public class PassportRequestDto {
    public Passport.PassportState passportState ;
    public String firstName;
    public String secondName;
    public LocalDateTime dateOfBirthday;
    public LocalDateTime expireDate;
    public String serialNumber;

}
