package com.example.demowithtests.service;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeeCabinetPrimaryKey;
import com.example.demowithtests.domain.EmployeesCabinetTable;
import com.example.demowithtests.repository.EmployeeCabinetRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeCabinetServiceBean implements EmployeeCabinetService {
    private final EmployeeCabinetRepository employeeCabinetRepository;
    @Override
    public EmployeesCabinetTable addRelation(Employee employee, Cabinet cabinet) {
        EmployeesCabinetTable employeesCabinetTable = getRelation(employee.getId(), cabinet.getId());
        if (employeesCabinetTable != null) {
            employeesCabinetTable.setActive(Boolean.TRUE);
        } else {
            employeesCabinetTable = new EmployeesCabinetTable();
            employeesCabinetTable.setEmployee(employee);
            employeesCabinetTable.setCabinet(cabinet);
        }
        return employeeCabinetRepository.save(employeesCabinetTable);
    }

    @Override
    public EmployeesCabinetTable getRelation(Integer employeeId, Integer cabinetId) {
        return employeeCabinetRepository
                .findById(new EmployeeCabinetPrimaryKey()
                        .setEmployeeId(employeeId)
                        .setCabinetId(cabinetId))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteRelation(Integer employeeId, Integer cabinetId) {
       var employeeCabinets =  getRelation(employeeId,cabinetId);
       if(employeeCabinets == null)
           throw new ResourceNotFoundException();
       employeeCabinets.setActive(Boolean.FALSE);
       employeeCabinetRepository.save(employeeCabinets);
    }
}
