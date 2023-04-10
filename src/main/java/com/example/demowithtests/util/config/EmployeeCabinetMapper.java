package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.EmployeesCabinetTable;
import com.example.demowithtests.dto.CabinetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeCabinetMapper {
    @Mapping(source = "cabinet", target = ".")
    CabinetResponse toResponse(EmployeesCabinetTable employeesCabinetsTable);
}
