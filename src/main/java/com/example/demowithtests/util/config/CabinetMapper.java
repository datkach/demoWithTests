package com.example.demowithtests.util.config;


import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.dto.CabinetRequest;
import com.example.demowithtests.dto.CabinetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CabinetMapper {



        CabinetMapper INSTANCE = Mappers.getMapper(CabinetMapper.class);

        Cabinet fromRequest(CabinetRequest request);

        CabinetResponse toResponse(Cabinet cabinet);

}