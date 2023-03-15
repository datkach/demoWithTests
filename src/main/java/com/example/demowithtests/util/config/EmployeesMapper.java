package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {
    EmployeesMapper INSTANCE = Mappers.getMapper(EmployeesMapper.class);

    Employee fromDto(EmployeeDto dto);

    EmployeeDto toDto(Employee employee);

    EmployeeReadDto toReadDto(Employee employee);

    List<EmployeeReadDto> toListReadDto(List<Employee> employees);

}
