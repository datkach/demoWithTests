package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class PassportResponseDto {
    public String firstName;
    public String secondName;
    public LocalDateTime dateOfBirthday;
    public LocalDateTime expireDate;

    public String serialNumber;


}
