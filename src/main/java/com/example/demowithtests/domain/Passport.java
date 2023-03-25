package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "passports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private String firstName;
    private String secondName;
    private LocalDateTime dateOfBirthday;
    private String serialNumber;
    private LocalDateTime expireDate;
    @OneToOne(mappedBy = "passport")
    private Employee employee;

}
