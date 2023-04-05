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
    @Enumerated(EnumType.STRING)
    private PassportState currentState;
    @OneToOne
    @JoinColumn(name = "previous_id", referencedColumnName = "id")
    private Passport previous;
    private Boolean isFree = Boolean.TRUE;
    public enum PassportState {
        NEW,
        ACTIVE,
        LOST,
        EXPIRED,
        CHANGED_FORMAT
    }
}
