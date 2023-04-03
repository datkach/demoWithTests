package com.example.demowithtests.web;

import com.example.demowithtests.dto.PassportRequestDto;
import com.example.demowithtests.dto.PassportResponseDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PassportController {
    PassportResponseDto savePassport(PassportRequestDto requestForSave);

    PassportResponseDto refreshPassport(Integer id, PassportRequestDto requestForSave);

    PassportResponseDto getPassportById(Integer id);

    void removePassportById(Integer id);

     List<PassportResponseDto> getAllFreePassports();
    void getHundredPassports();
    PassportResponseDto getFirstFreePassports();

}
