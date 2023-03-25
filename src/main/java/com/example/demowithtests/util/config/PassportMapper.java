package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassportMapper {
   PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);

    Passport fromDto(PassportRequestDto dto);

    PassportResponseDto toDto(Passport passport);
}
