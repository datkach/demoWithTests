package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.Country;
import com.example.demowithtests.util.annotations.Name;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Name
    private String name;
    @Country
    private String country;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Photo> photos = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean isPrivate = Boolean.FALSE;
    @OneToMany(mappedBy = "employee")
    private Set<EmployeesCabinetTable> employeesCabinets = new HashSet<>();
}
