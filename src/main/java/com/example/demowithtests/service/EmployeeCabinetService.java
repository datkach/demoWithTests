package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesCabinetTable;

public interface EmployeeCabinetService {
    EmployeesCabinetTable addRelation(Employee employee, Cabinet cabinet);

    EmployeesCabinetTable getRelation(Integer employeeId, Integer cabinetId);

    void deleteRelation(Integer employeeId, Integer cabinetId);
}
