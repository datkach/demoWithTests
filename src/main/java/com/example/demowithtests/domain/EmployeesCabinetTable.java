package com.example.demowithtests.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "users_cabinets")
@Getter
@Setter
public class EmployeesCabinetTable {
    @EmbeddedId
    EmployeeCabinetPrimaryKey id = new EmployeeCabinetPrimaryKey();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("employeeId")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "cabinet_id")
    @MapsId("cabinetId")
    private Cabinet cabinet;

    Boolean active;
}
