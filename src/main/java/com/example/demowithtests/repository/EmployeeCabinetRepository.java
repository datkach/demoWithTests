package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeeCabinetPrimaryKey;
import com.example.demowithtests.domain.EmployeesCabinetTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCabinetRepository extends JpaRepository<EmployeesCabinetTable, EmployeeCabinetPrimaryKey> {

}
