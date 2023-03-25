package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Employee;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

public class PassportRequestDto {

    public String firstName;
    public String secondName;
    public LocalDateTime dateOfBirthday;
    public LocalDateTime expireDate;

}
